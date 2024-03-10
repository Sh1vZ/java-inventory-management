package dao;

import configuration.JPAConfiguration;
import interace.BaseDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class BaseDaoImpl<T, I> implements BaseDAO<T, I> {

    protected final Class<T> TClass;
    protected final EntityManagerFactory emf;

    public BaseDaoImpl(Class<T> tClass) {
        TClass = tClass;
        this.emf = JPAConfiguration.getEntityManagerFactory();
    }

    @Override
    public T save(T e) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(e);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

        return e;
    }

    @Override
    public T findById(I id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(TClass, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<T> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(TClass);
            Root<T> root = criteriaQuery.from(TClass);
            criteriaQuery.select(root);
            return em.createQuery(criteriaQuery).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public T update(T e) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        T updatedEntity = null;
        try {
            transaction.begin();
            updatedEntity = em.merge(e);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return updatedEntity;
    }

    @Override
    public T deleteByid(I id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        T deletedEntity = null;
        try {
            transaction.begin();
            T le = em.find(TClass, id);
            if (le != null) {
                em.remove(le);
                deletedEntity = le;
            }
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return deletedEntity;
    }
}
