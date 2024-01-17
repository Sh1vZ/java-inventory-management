package menu;

import entity.Customer;
import entity.Inventory;
import service.InventoryService;

import java.util.Scanner;

public class InventoryMenuHandler extends BaseMenuHandler {

    InventoryService inventoryService;

    public InventoryMenuHandler(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void handleMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("\nInventory Service Menu:");
            System.out.println("1. Update inventory");
            System.out.println("0. Back to Main Menu");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    updateInventory(scanner);
                    break;
                case 0:
                    System.out.println("Returning to the main menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private Inventory getValidId(Scanner scanner) {
        Inventory inv=null;
        Long id=null;
        do {
            id = getValidLong(scanner,"Enter inventory id: ");
            inv=inventoryService.getInventoryById(id);
            scanner.nextLine();
            if (inv==null) {
                System.out.println("Inventory ID does not exist. Please enter a valid ID.");
            }
        } while (inv==null);
        return inv;
    }

    private void updateInventory(Scanner scanner) {
        Integer size = inventoryService.printAllInventory();
        if(size ==0){
            return;
        }
        Inventory inv = getValidId(scanner);
        System.out.print("Enter customer name: ");
        Long amount = scanner.nextLong();
        inv.setAmount(amount);
        inventoryService.updateInventory(inv);
        System.out.println("Customer updated successfully!");

    }
}

