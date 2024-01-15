import dao.CustomerDAOImpl;
import dao.InventoryDAOImpl;
import dao.ProductDAOImpl;
import entity.*;
import interace.CustomerDAO;
import interace.InventoryDAO;
import interace.ProductDAO;
import service.CustomerService;
import service.InventoryService;
import service.ProductService;
import service.TransactionService;

import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        CustomerService customerService = new CustomerService();
        ProductService productService = new ProductService();
        InventoryService invService= new InventoryService();
        TransactionService txService=new TransactionService();

        Customer customer = new Customer();
        customer.setName("John Doe");
        customerService.createCustomer(customer);



        Product product = new Product();
        product.setName("Smartphone X");
        product.setPrice(500L);
        product.setSku("SKU001");
        product.setColor("Black");
        product.setSize("Medium");
        product.setType("Mobile");
        productService.saveProduct(product);

        Inventory inv= new Inventory();
        inv.setAmount(5L);
        inv.setProduct(product);
        invService.createInventory(inv);



        Transaction tx=new Transaction();
        tx.setCustomer(customer);
        txService.saveTx(tx);

        TransactionProduct lineProd=new TransactionProduct();
        TransactionProductId prodid=new TransactionProductId();
        prodid.setProductId(product.getId());
        prodid.setTransactionId(tx.getId());
        lineProd.setId(prodid);
        lineProd.setQuantity(1L);
        txService.saveTxProd(lineProd);

//                Customer customer = customerService.getCustomerById(1L);
//        customer.setName("big john");
//        customerDAO.updateCustomer(customer);
//        customerService.deleteCustomer(1L);
        invService.reportInventory();
    }
}

