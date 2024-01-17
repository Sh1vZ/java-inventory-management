package dao;

import entity.*;
import interace.TransactionDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class TransactionDaoImpl extends BaseDaoImpl<Transaction, Long> implements TransactionDAO {

    public TransactionDaoImpl() {
        super(Transaction.class);
    }


    @Override
    public List<Object[]> countOrderPerUser() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Object[]> query = em.createQuery("SELECT c.name, COUNT(t),SUM(t.total) " + "FROM Transaction t " + "JOIN t.customer c " + "GROUP BY c.name", Object[].class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Transaction createTransaction(Customer customer, List<LineItem> items, Long txTotal) {
        EntityManager em = emf.createEntityManager();
        Transaction tx = new Transaction();
        try {
            em.getTransaction().begin();
            tx.setCustomer(customer);
            tx.setTotal(txTotal);
            em.persist(tx);
            for (LineItem item : items) {
                TransactionProduct lineProd = new TransactionProduct();
                TransactionProductId prodid = new TransactionProductId();
                prodid.setProductId(item.getProduct().getId());
                prodid.setTransactionId(tx.getId());
                lineProd.setId(prodid);
                lineProd.setQuantity(item.getAmount());
                TypedQuery<Inventory> query = em.createQuery("SELECT p FROM Inventory p WHERE p.product.id = :product_id", Inventory.class);
                query.setParameter("product_id", item.getProduct().getId());
                Inventory inv = query.getSingleResult();
                Long newAmount = inv.getAmount() - item.getAmount();
                inv.setAmount(newAmount);
                em.persist(lineProd);
                em.merge(inv);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

        return tx;
    }
}
