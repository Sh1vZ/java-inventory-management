package configuration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAConfiguration {

    private static final String PERSISTENCE_UNIT_NAME = "inv_management";
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    private static final EntityManager entityManager = factory.createEntityManager();

    public static EntityManagerFactory getEntityManagerFactory() {
        return factory;
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }

    public static void shutdown() {
        if (entityManager != null) {
            entityManager.close();
        }
        if (factory != null) {
            factory.close();
        }
    }
}
