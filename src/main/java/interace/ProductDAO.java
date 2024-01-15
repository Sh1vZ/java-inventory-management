package interace;

import entity.Product;

import java.util.List;

public interface ProductDAO {
    Product saveProduct(Product Product);

    Product getProductById(Long id);

    void updateProduct(Product product);
     List<Product> searchProductByName(String searchTerm);
    void deleteProduct(Long id);
}
