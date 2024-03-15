package dao;

import entity.Customer;
import interace.CustomerDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class CustomerDAOImpl extends BaseDaoImpl<Customer, Long> implements CustomerDAO {

    public CustomerDAOImpl() {
        super(Customer.class);
    }

    @Override
    public Long countAllCustomers() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(t) FROM Customer t", Long.class);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}
