package menu;

import entity.Customer;
import service.CustomerService;

import java.util.Scanner;

public class CustomerMenuHandler extends BaseMenuHandler {

    private final CustomerService customerService;

    public CustomerMenuHandler(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void handleMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("\nCustomer Service Menu:");
            System.out.println("1. Create Customer");
            System.out.println("2. Find all customers");
            System.out.println("3. Get Customer by ID");
            System.out.println("4. Update Customer");
            System.out.println("5. Delete Customer");
            System.out.println("0. Back to Main Menu");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    createCustomer(scanner);
                    break;
                case 2:
                    customerService.printAllCustomers();
                    break;
                case 3:
                    findCustomer(scanner);
                    break;
                case 4:
                    updateCustomer(scanner);
                    break;
                case 5:
                    deleteCustomer(scanner);
                    break;
                case 0:
                    System.out.println("Returning to the main menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private void createCustomer(Scanner scanner) {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();

        Customer customer = new Customer();
        customer.setName(name);
        customerService.createCustomer(customer);

        System.out.println("Customer created successfully!");
    }

    private void findCustomer(Scanner scanner) {
        Long id =getValidLong(scanner,"Enter customer id: ");
        customerService.findCustomerAndPrintout(id);
    }



    private void updateCustomer(Scanner scanner) {
        Integer size = customerService.printAllCustomers();
        if(size ==0){
            return;
        }
        Customer customer = getValidCustomerId(scanner);
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        customer.setName(name);
        customerService.updateCustomer(customer);
        System.out.println("Customer updated successfully!");

    }

    private void deleteCustomer(Scanner scanner) {
        Integer size = customerService.printAllCustomers();
        if(size ==0){
            return;
        }
        Customer customer = getValidCustomerId(scanner);
        customerService.deleteCustomer(customer.getId());
        System.out.println("Customer deleted successfully!");

    }
}
