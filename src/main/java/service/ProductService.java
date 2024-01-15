package service;

import dao.InventoryDAOImpl;
import dao.ProductDAOImpl;
import entity.Product;
import interace.InventoryDAO;
import interace.ProductDAO;

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
}
