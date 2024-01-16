package service;

import dao.InventoryDAOImpl;
import entity.Inventory;
import entity.Product;
import interace.InventoryDAO;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class InventoryService {
    private final InventoryDAO inventoryDAO;

    public InventoryService() {
        this.inventoryDAO = new InventoryDAOImpl();
    }

    public Inventory createInventory(Inventory inv) {
        return inventoryDAO.save(inv);
    }

    public Inventory getInventoryById(Long id) {
        return inventoryDAO.findById(id);
    }

    public Inventory getInventoryByProductId(Long id) {
        return inventoryDAO.getInvByProdId(id);
    }

    public void getInventoryByProductId(Inventory inventory) {
        inventoryDAO.update(inventory);
    }

    public void reportInventory() {
        List<Inventory> inventories = inventoryDAO.findAll();
        System.out.println(StringUtils.center("Product Inventory", 50, "="));
        System.out.printf("%-10s %-20s %-20s%n",
                "Product ID", "Product Name", "Stock Amount");
        System.out.println(StringUtils.repeat("-", 60));

        for (Inventory inventory : inventories) {
            Product product = inventory.getProduct();
            System.out.printf("%-10s %-20s %-20s%n",
                    product.getId(), product.getName(), inventory.getAmount());
        }
    }

}
