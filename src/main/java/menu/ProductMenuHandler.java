package menu;

import entity.Product;
import service.InventoryService;
import service.ProductService;

import java.util.Scanner;

public class ProductMenuHandler extends BaseMenuHandler {
    private final ProductService productService;

    public ProductMenuHandler(ProductService productService) {
        this.productService = productService;
    }

    public void handleMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("\nCustomer Service Menu:");
            System.out.println("1. Create product");
            System.out.println("2. Find all products");
            System.out.println("3. Get product by ID");
            System.out.println("4. Update product");
            System.out.println("5. Delete product");
            System.out.println("0. Back to Main Menu");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    createProduct(scanner);
                    break;
                case 2:
                    productService.printAllProducts();
                    break;
                case 3:
                    findProduct(scanner);
                    break;
                case 4:
                    updateProduct(scanner);
                    break;
                case 5:
                    deleteProduct(scanner);
                    break;
                case 0:
                    System.out.println("Returning to the main menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private void createProduct(Scanner scanner) {
        System.out.println("Enter product details:");

        System.out.print("Name: ");
        String name = getNonEmptyInput(scanner, "Name is mandatory. Please enter a name.");

        Long price = getValidLong(scanner, "Price: ");

        System.out.print("SKU: ");
        String sku = getNonEmptyInput(scanner, "SKU is mandatory. Please enter a SKU.");

        System.out.print("Supplier(press Enter to skip): ");
        String supplier = scanner.nextLine();

        System.out.print("Color(press Enter to skip): ");
        String color = scanner.nextLine();

        System.out.print("Size(press Enter to skip): ");
        String size = scanner.nextLine();

        System.out.print("Type(press Enter to skip): ");
        String type = scanner.nextLine();

        Product product = Product.builder().name(name).price(price).sku(sku).supplier(supplier).color(color).size(size).type(type).build();

        productService.saveProduct(product);
        System.out.println("Product created successfully!");
    }

    private void updateProduct(Scanner scanner) {
        Integer dataSize = productService.printAllProducts();
        if (dataSize == 0) {
            return;
        }
        Product prod = getValidProduct(scanner);
        System.out.println("Enter product details:");

        System.out.print("Name(press Enter to skip): ");
        String name = scanner.nextLine();

        Long price = getValidLong(scanner,"Enter price (press Enter to skip): ");

        System.out.print("SKU(press Enter to skip): ");
        String sku = scanner.nextLine();

        System.out.print("Supplier(press Enter to skip): ");
        String supplier = scanner.nextLine();

        System.out.print("Color(press Enter to skip): ");
        String color = scanner.nextLine();

        System.out.print("Size(press Enter to skip): ");
        String size = scanner.nextLine();

        System.out.print("Type(press Enter to skip): ");
        String type = scanner.nextLine();

        prod.setName((name != null && !name.isEmpty()) ? name : prod.getName());
        prod.setPrice((price != null) ? price : prod.getPrice());
        prod.setSku((sku != null && !sku.isEmpty()) ? sku : prod.getSku());
        prod.setSupplier((supplier != null && !supplier.isEmpty()) ? supplier : prod.getSupplier());
        prod.setColor((color != null && !color.isEmpty()) ? color : prod.getColor());
        prod.setSize((size != null && !size.isEmpty()) ? size : prod.getSize());
        prod.setType((type != null && !type.isEmpty()) ? type : prod.getType());


        productService.updateProduct(prod);
        System.out.println("Product updated successfully!");
    }

    private void findProduct(Scanner scanner) {
        Long id = getValidLong(scanner,"Enter product id: ");
        productService.findProductAndPrintout(id);
    }

    private void deleteProduct(Scanner scanner) {
        Integer size = productService.printAllProducts();
        if (size == 0) {
            return;
        }
        Product product = getValidProduct(scanner);
        productService.deleteProduct(product.getId());
        System.out.println("Product deleted successfully!");

    }
}
