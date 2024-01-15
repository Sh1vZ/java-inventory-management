package interace;

import entity.Customer;
import entity.Inventory;
import entity.Transaction;

import java.util.List;

public interface InventoryDAO {
    Inventory saveInventory(Inventory inventory);

    Inventory getInvById(Long id);
    Inventory getInvByProdId(Long id);

    List<Inventory> findAll();
    void updateInv(Inventory inventory);
}
