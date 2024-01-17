package interace;

import entity.Inventory;
import entity.Product;

import java.util.List;

public interface ProductDAO extends BaseDAO<Product, Long> {
    Product save(Product e, Inventory i);
    List<Product> searchProductByName(String searchTerm);
}
