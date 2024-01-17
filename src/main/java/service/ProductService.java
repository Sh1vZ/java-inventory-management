package service;

import dao.ProductDAOImpl;
import entity.Customer;
import entity.Inventory;
import entity.Product;
import interace.ProductDAO;

import java.util.List;

public class ProductService {

    private final ProductDAO prodDAO;

    public ProductService() {
        this.prodDAO = new ProductDAOImpl();
    }

    public Product saveProduct(Product product) {
        Inventory inv=new Inventory();
        inv.setProduct(product);
        inv.setAmount(0L);
        return prodDAO.save(product,inv);
    }

    public Product getProductById(Long id) {
        return prodDAO.findById(id);
    }

    public void updateProduct(Product product) {
        prodDAO.update(product);
    }

    public void deleteProduct(Long id) {
        prodDAO.deleteByid(id);
    }

    public List<Product> findAllProducts() {
        return prodDAO.findAll();
    }
    public void printProduct(Product prod) {
        System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-20s%n", prod.getId(), prod.getName(),prod.getSupplier(),prod.getPrice(),prod.getSku(),prod.getColor(),prod.getSize(),prod.getType(),prod.getCreatedAt(),prod.getUpdatedAt());
    }

    public void findProductAndPrintout(Long id) {
        System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-20s%n",
                "Product ID", "Name", "Supplier", "Price", "SKU", "Color", "Size", "Type",
                "Created At", "Updated At");
        printProduct(getProductById(id));
    }

    public int printAllProducts() {
        List<Product> products = findAllProducts();
        System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-20s%n",
                "Product ID", "Name", "Supplier", "Price", "SKU", "Color", "Size", "Type",
                "Created At", "Updated At");
        if(products.isEmpty()){
            System.out.println("No customers found");
            return 0;
        }
        for (Product product : products) {
            printProduct(product);
        }
        return products.size();
    }
    public void searchAndPrintProductByName(String searchTerm) {
        List<Product> products = prodDAO.searchProductByName(searchTerm);

        System.out.println("Search results for product name containing '" + searchTerm + "':");
        System.out.println("---------------------------------------------------");
        for (Product product : products) {
            System.out.println("Product ID: " + product.getId());
            System.out.println("Product Name: " + product.getName());
            System.out.println("Supplier: " + product.getSupplier());
            System.out.println("Price: " + product.getPrice());
            System.out.println("SKU: " + product.getSku());
            System.out.println("Color: " + product.getColor());
            System.out.println("Size: " + product.getSize());
            System.out.println("Type: " + product.getType());
            System.out.println("Created At: " + product.getCreatedAt());
            System.out.println("Updated At: " + product.getUpdatedAt());
            System.out.println("-----------------------------------------");
        }
    }
}
