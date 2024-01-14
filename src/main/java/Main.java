import entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class Main {

    public static void main(String[] args) {
        // Create an EntityManagerFactory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("inv_management");

        // Create an EntityManager
        EntityManager em = emf.createEntityManager();

        // Begin a transaction
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            // Example: Inserting data into all tables

            // 3. Inserting a Product
            Product product = new Product();
            product.setName("Smartphone X");
            product.setPrice(500L);
            product.setSku("SKU001");
            product.setColor("Black");
            product.setSize("Medium");
            product.setType("Mobile");
            em.persist(product);

            // 4. Inserting an Inventory
            Inventory inventory = new Inventory();
            inventory.setProduct(product); // Use the ID of the created product
            inventory.setAmount(100L);
            em.persist(inventory);

            // 5. Inserting a Customer
            Customer customer = new Customer();
            customer.setName("John Doe");
            em.persist(customer);

            // 6. Inserting a Transaction
            entity.Transaction transactionEntity = new entity.Transaction();
            transactionEntity.setCustomer(customer);
            em.persist(transactionEntity);

            // 7. Inserting a TransactionProduct
            TransactionProduct transactionProduct = new TransactionProduct();
            TransactionProductId transactionProductId = new TransactionProductId();
            transactionProductId.setTransactionId(transactionEntity.getId());
            transactionProductId.setProductId(product.getId()); // Use the ID of the created product
            transactionProduct.setId(transactionProductId);
            transactionProduct.setTransaction(transactionEntity);
            transactionProduct.setProduct(product);
            transactionProduct.setQuantity(2L);
            em.persist(transactionProduct);

            // Commit the transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            // Close the EntityManager and EntityManagerFactory
            em.close();
            emf.close();
        }
    }
}

