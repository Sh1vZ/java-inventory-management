package dao;

import configuration.JPAConfiguration;
import entity.Inventory;
import entity.Product;
import entity.Transaction;
import interace.TransactionDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class TransactionDaoImpl implements TransactionDAO {
    private EntityManagerFactory emf;

    public TransactionDaoImpl() {
        this.emf =  JPAConfiguration.getEntityManagerFactory();
    }
    @Override
    public Transaction findTxById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Transaction.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Transaction> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Transaction> query = em.createQuery("SELECT t FROM Transaction t", Transaction.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Object[]> countOrderPerUser(Long userId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Object[]> query = em.createQuery("SELECT c.name, COUNT(t) " +
                    "FROM Transaction t " +
                    "JOIN t.customer c " +
                    "WHERE t.customer.id = :userId GROUP BY c.name", Object[].class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Transaction saveTx(Transaction transaction) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(transaction);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

        return transaction;
    }
}
