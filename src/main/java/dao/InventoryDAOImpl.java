package dao;

import configuration.JPAConfiguration;
import entity.Customer;
import entity.Inventory;
import entity.Transaction;
import interace.InventoryDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class InventoryDAOImpl implements InventoryDAO {

    private EntityManagerFactory emf;

    public InventoryDAOImpl() {
        this.emf =  JPAConfiguration.getEntityManagerFactory();
    }
    @Override
    public Inventory saveInventory(Inventory inventory) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(inventory);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

        return inventory;
    }

    @Override
    public Inventory getInvById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Inventory.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Inventory> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Inventory> query = em.createQuery("SELECT i FROM Inventory i", Inventory.class);
            return query.getResultList();
        } finally {
            em.close();
        }
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

    @Override
    public void updateInv(Inventory inventory) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(inventory);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
