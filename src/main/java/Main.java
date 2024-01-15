import entity.*;
import service.CustomerService;
import service.InventoryService;
import service.ProductService;
import service.TransactionService;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        CustomerService customerService = new CustomerService();
        ProductService productService = new ProductService();
        InventoryService invService = new InventoryService();
        TransactionService txService = new TransactionService();

        Customer customer = new Customer();
        customer.setName("John Doe");
        customerService.createCustomer(customer);


        Product product = Product.builder()
                .name("Sample Product")
                .supplier("asd")
                .price(100L)
                .sku("SKU123")
                .color("Red")
                .size("Medium")
                .type("Electronics")
                .build();

        productService.saveProduct(product);

        Inventory inv = new Inventory();
        inv.setAmount(5L);
        inv.setProduct(product);
        invService.createInventory(inv);

        List<LineItem> products=new ArrayList<>();
        products.add(new LineItem(product,3L));

        txService.createTransaction(customer,products);

//        Customer customer = customerService.getCustomerById(1L);
//        customer.setName("big john");
//        customerService.updateCustomer(customer);
//        customerService.deleteCustomer(1L);

//        invService.reportInventory();
//        txService.reportUserTx(1L);
//        productService.searchAndPrintProductByName("Sample");
    }
}

