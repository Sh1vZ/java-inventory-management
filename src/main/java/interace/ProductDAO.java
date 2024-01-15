package interace;

import entity.Product;

public interface ProductDAO {
    Product saveProduct(Product Product);

    Product getProductById(Long id);

    void updateProduct(Product product);

    void deleteProduct(Long id);
}
