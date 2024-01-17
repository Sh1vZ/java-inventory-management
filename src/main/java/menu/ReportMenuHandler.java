package menu;

import service.CustomerService;
import service.InventoryService;
import service.ProductService;
import service.TransactionService;

import java.util.Scanner;

public class ReportMenuHandler {

    TransactionService transactionService;
    InventoryService inventoryService;

    public ReportMenuHandler(TransactionService transactionService, InventoryService inventoryService) {
        this.transactionService = transactionService;
        this.inventoryService = inventoryService;
    }

    public void handleMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("\nReport Service Menu:");
            System.out.println("1. Report inventory");
            System.out.println("2. Report user transactions");
            System.out.println("0. Back to Main Menu");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    inventoryService.printAllInventory();
                    break;
                case 2:
                    transactionService.reportCustomerTx();
                    break;
                case 0:
                    System.out.println("Returning to the main menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }
}
