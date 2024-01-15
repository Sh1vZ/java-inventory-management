package service;

import dao.InventoryDAOImpl;
import dao.ProductDAOImpl;
import entity.Product;
import interace.InventoryDAO;
import interace.ProductDAO;

import java.util.List;

public class ProductService {

    private ProductDAO prodDAO;

    public ProductService() {
        this.prodDAO = new ProductDAOImpl();
    }
    public Product saveProduct(Product product) {
        return prodDAO.saveProduct(product);
    }

    public Product getProductById(Long id) {
        return prodDAO.getProductById(id);
    }

    public void updateProduct(Product product) {
        prodDAO.updateProduct(product);
    }
    public void deleteProduct(Long id) {
        prodDAO.deleteProduct(id);
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
