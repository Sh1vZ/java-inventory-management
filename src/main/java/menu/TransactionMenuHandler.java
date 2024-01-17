package menu;

import entity.Customer;
import entity.LineItem;
import entity.Product;
import service.CustomerService;
import service.ProductService;
import service.TransactionService;

import java.util.*;

public class TransactionMenuHandler extends BaseMenuHandler {

    TransactionService transactionService;
    CustomerService customerService;
    ProductService productService;

    public TransactionMenuHandler(TransactionService transactionService, CustomerService customerService, ProductService productService) {
        this.transactionService = transactionService;
        this.customerService = customerService;
        this.productService = productService;
    }

    public void handleMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("\nTransaction Service Menu:");
            System.out.println("1. Create transaction");
            System.out.println("0. Back to Main Menu");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    createTransaction(scanner);
                    break;
                case 0:
                    System.out.println("Returning to the main menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private boolean shouldContinue(Scanner scanner) {
        System.out.print("Do you want to add more products? (yes/no): ");
        String userChoice = scanner.nextLine().toLowerCase();
        return userChoice.equals("yes");
    }

    private LineItem addProduct(Scanner scanner) {
        Integer size = productService.printAllProducts();
        if (size == 0) {
            return null;
        }
        Product prod = getValidProduct(scanner);
        Long amount = getValidLong(scanner,"Amount: ");

        return new LineItem(prod, amount);
    }

    private Set<LineItem> prompAdd(Scanner scanner) {
        Set<LineItem> lineItems = new HashSet<>();
        do {
            LineItem item = addProduct(scanner);
            if (item != null) {
                lineItems.add(item);
            }
            scanner.nextLine();
        } while (shouldContinue(scanner));
        return lineItems;
    }

    private void createTransaction(Scanner scanner) {
        Integer size = customerService.printAllCustomers();
        if (size == 0) {
            return;
        }
        Customer customer = getValidCustomerId(scanner);
        Set<LineItem> lineItems = prompAdd(scanner);
        transactionService.createTransaction(customer,lineItems);
        System.out.print("Transaction created");
    }
}


