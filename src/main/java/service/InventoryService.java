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

    public Inventory getInventoryById(Long id) {
        return inventoryDAO.findById(id);
    }


    public Inventory getInventoryByProductId(Long id) {
        return inventoryDAO.getInvByProdId(id);
    }

    public Inventory updateInventory(Inventory inventory) {
        return inventoryDAO.update(inventory);
    }

    public void printInventory(Inventory inventory) {
        Product product = inventory.getProduct();
        System.out.printf("%-10s %-20s %-20s%n",
                inventory.getId(), product.getName(), inventory.getAmount());
    }

    public List<Inventory> getInventories() {
        return inventoryDAO.findAll();
    }

    public Integer printAllInventory() {
        List<Inventory> inventories = inventoryDAO.findAll();
        if(inventories.isEmpty()){
            System.out.println("Inventory empty");
            return 0;
        }
        System.out.println(StringUtils.center("Product Inventory", 50, "="));
        System.out.printf("%-10s %-20s %-20s%n",
                "Inventory ID", "Product Name", "Stock Amount");
        System.out.println(StringUtils.repeat("-", 60));

        for (Inventory inventory : inventories) {
            printInventory(inventory);
        }
        return inventories.size();
    }

}
