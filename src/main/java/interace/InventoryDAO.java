package interace;

import entity.Inventory;

public interface InventoryDAO extends BaseDAO<Inventory, Long> {
    Inventory getInvByProdId(Long id);
}
