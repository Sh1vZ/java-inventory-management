package dao;

import entity.TransactionProduct;
import interace.TransactionDAO;
import interace.TransactionProductDAO;
import jakarta.persistence.EntityManager;

public class TransactionProductDAOImpl extends BaseDaoImpl<TransactionDAO, Long> implements TransactionProductDAO {
    public TransactionProductDAOImpl() {
        super(TransactionDAO.class);
    }

    @Override
    public TransactionProduct saveTxProd(TransactionProduct txProd) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(txProd);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

        return txProd;

    }
}
