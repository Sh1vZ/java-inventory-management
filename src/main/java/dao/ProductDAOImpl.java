package dao;

import entity.Inventory;
import entity.Product;
import interace.ProductDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ProductDAOImpl extends BaseDaoImpl<Product, Long> implements ProductDAO {

    public ProductDAOImpl() {
        super(Product.class);
    }


    @Override
    public Product save(Product e, Inventory i) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(e);
            em.persist(i);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

        return e;
    }

    public List<Product> searchProductByName(String searchTerm) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p " +
                    "WHERE LOWER(p.name) LIKE LOWER(:searchTerm)", Product.class);
            query.setParameter("searchTerm", "%" + searchTerm + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

}
