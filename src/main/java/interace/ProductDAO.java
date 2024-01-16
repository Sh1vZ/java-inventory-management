package interace;

import entity.Product;

import java.util.List;

public interface ProductDAO extends BaseDAO<Product, Long> {
    List<Product> searchProductByName(String searchTerm);
}
