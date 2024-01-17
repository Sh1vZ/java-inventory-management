import menu.*;
import service.CustomerService;
import service.InventoryService;
import service.ProductService;
import service.TransactionService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        CustomerService customerService = new CustomerService();
        ProductService productService = new ProductService();
        InventoryService invService = new InventoryService();
        TransactionService txService = new TransactionService();
        CustomerMenuHandler customerMenuHandler = new CustomerMenuHandler(customerService);
        ProductMenuHandler productMenuHandler = new ProductMenuHandler(productService);
        SearchMenuHandler searchMenuHandler = new SearchMenuHandler(productService);
        ReportMenuHandler reportMenuHandler = new ReportMenuHandler(txService,invService);
        InventoryMenuHandler inventoryMenuHandler = new InventoryMenuHandler(invService);
        TransactionMenuHandler transactionMenuHandler = new TransactionMenuHandler(txService,customerService,productService);


        int choice;
        do {
            System.out.println("Main Menu:");
            System.out.println("1. Customer Service");
            System.out.println("2. Inventory Service");
            System.out.println("3. Product Service");
            System.out.println("4. Transaction Service");
            System.out.println("5. Report Service");
            System.out.println("6. Search Service");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    customerMenuHandler.handleMenu(scanner);
                    break;
                case 2:
                    inventoryMenuHandler.handleMenu(scanner);

                    break;
                case 3:
                    productMenuHandler.handleMenu(scanner);
                    break;
                case 4:
                    transactionMenuHandler.handleMenu(scanner);
                    break;
                case 5:
                    reportMenuHandler.handleMenu(scanner);
                    break;
                case 6:
                    searchMenuHandler.handleMenu(scanner);
                    break;
                case 0:
                    System.out.println("Exiting the application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }
}

