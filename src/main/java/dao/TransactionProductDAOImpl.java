package dao;

import configuration.JPAConfiguration;
import entity.Transaction;
import entity.TransactionProduct;
import interace.TransactionProductDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class TransactionProductDAOImpl implements TransactionProductDAO {

    private EntityManagerFactory emf;

    public TransactionProductDAOImpl() {
        this.emf =  JPAConfiguration.getEntityManagerFactory();
    }

    @Override
    public TransactionProduct saveTxProd(TransactionProduct txProd) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(txProd);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

        return txProd;

    }
}
