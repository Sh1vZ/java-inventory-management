package dao;

import entity.Inventory;
import interace.InventoryDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class InventoryDAOImpl extends BaseDaoImpl<Inventory, Long> implements InventoryDAO {

    public InventoryDAOImpl() {
        super(Inventory.class);
    }

    @Override
    public Inventory getInvByProdId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Inventory> query = em.createQuery("SELECT p FROM Inventory p WHERE p.product.id = :product_id", Inventory.class);
            query.setParameter("product_id", id);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

}
