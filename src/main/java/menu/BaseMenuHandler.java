package menu;

import entity.Customer;
import entity.Product;
import service.CustomerService;
import service.ProductService;

import java.util.Scanner;

public class BaseMenuHandler {
    protected String getNonEmptyInput(Scanner scanner, String errorMessage) {
        String input;
        do {
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println(errorMessage);
            }
        } while (input.isEmpty());
        return input;
    }

    protected Long getNonEmptyLongInput(Scanner scanner, String errorMessage) {
        Long input;
        do {
            input = scanner.nextLong();
            if (input <= 0) {
                System.out.println(errorMessage);
            }
        } while (input <= 0);
        return input;
    }

    protected Long getValidPrice(Scanner scanner) {
        String priceInput;
        Long price = null;
        do {
            System.out.print("Enter price (press Enter to skip): ");
            priceInput = scanner.nextLine();
            if (!priceInput.isEmpty()) {
                try {
                    price = Long.parseLong(priceInput);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid price format. Please enter a valid number.");
                }
            }
        } while (!priceInput.isEmpty() && price==null);
        return price;
    }


    protected Long getValidLong(Scanner scanner, String msg) {
        String priceInput;
        Long price = null;
        do {
            System.out.print(msg);
            priceInput = scanner.nextLine();
            if (!priceInput.isEmpty()) {
                try {
                    price = Long.parseLong(priceInput);
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                    scanner.nextLine();
                }
            }
        } while (price == null);
        return price;
    }

    protected Customer getValidCustomerId(Scanner scanner) {
        Customer cus = null;
        Long id = null;
        CustomerService customerService = new CustomerService();
        do {
            System.out.print("Enter customer id: ");
            id = scanner.nextLong();
            cus = customerService.getCustomerById(id);
            scanner.nextLine();
            if (cus == null) {
                System.out.println("Customer ID does not exist. Please enter a valid ID.");
            }
        } while (cus == null);
        return cus;
    }

    protected Product getValidProduct(Scanner scanner) {
        Product prod = null;
        Long id = null;
        ProductService productService = new ProductService();
        do {
            System.out.print("Enter product id: ");
            try {
                id = scanner.nextLong();
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine();
            }
            prod = productService.getProductById(id);
            scanner.nextLine();
            if (prod == null) {
                System.out.println("Product ID does not exist. Please enter a valid ID.");
            }
        } while (prod == null);
        return prod;
    }
}
