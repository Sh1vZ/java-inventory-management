package menu;

import entity.Customer;
import entity.LineItem;
import service.ProductService;

import java.util.Scanner;
import java.util.Set;

public class SearchMenuHandler {
    ProductService productService;

    public SearchMenuHandler(ProductService productService) {
        this.productService = productService;
    }

    public void handleMenu(Scanner scanner) {



        int choice;
        do {
            System.out.println("\nSearch Service Menu:");
            System.out.println("1. Search product");
            System.out.println("0. Back to Main Menu");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    searchProduct(scanner);
                    break;
                case 0:
                    System.out.println("Returning to the main menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }
    private void searchProduct(Scanner scanner) {
        String searchString= scanner.nextLine();
        productService.searchAndPrintProductByName(searchString);
    }
}
