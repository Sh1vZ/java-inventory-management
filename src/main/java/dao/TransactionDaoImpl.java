package dao;

import entity.*;
import interace.TransactionDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public Long countAllTransactions() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(t) FROM Transaction t", Long.class);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public Long sumTransactions() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery("SELECT SUM(total) FROM Transaction t", Long.class);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Transaction> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            // Create query to fetch all transactions along with their associated transaction products and customer information
            return em.createQuery("SELECT DISTINCT t FROM Transaction t LEFT JOIN FETCH t.transactionProducts LEFT JOIN FETCH t.customer", Transaction.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Transaction createTransaction(Customer customer, List<LineItem> items, Long txTotal) {
        EntityManager em = emf.createEntityManager();
        Transaction tx = new Transaction();
        Set<TransactionProduct> transactionProducts = new HashSet<>();
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
                lineProd.setProduct(item.getProduct());
                lineProd.setQuantity(item.getAmount());
                TypedQuery<Inventory> query = em.createQuery("SELECT p FROM Inventory p WHERE p.product.id = :product_id", Inventory.class);
                query.setParameter("product_id", item.getProduct().getId());
                Inventory inv = query.getSingleResult();
                Long newAmount = inv.getAmount() - item.getAmount();
                inv.setAmount(newAmount);
                em.persist(lineProd);
                em.merge(inv);
                transactionProducts.add(lineProd);
            }
            em.getTransaction().commit();
            tx.setTransactionProducts(transactionProducts);
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

        return tx;
    }
}
