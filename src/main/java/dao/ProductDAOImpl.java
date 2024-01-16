package dao;

import entity.Product;
import interace.ProductDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ProductDAOImpl extends BaseDaoImpl<Product, Long> implements ProductDAO {

    public ProductDAOImpl() {
        super(Product.class);
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
