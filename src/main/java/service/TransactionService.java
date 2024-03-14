package service;

import dao.TransactionDaoImpl;
import dao.TransactionProductDAOImpl;
import entity.*;
import interace.TransactionDAO;
import interace.TransactionProductDAO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TransactionService {
    private final TransactionDAO txDao;
    private final TransactionProductDAO txProdDao;

    public TransactionService() {
        this.txDao = new TransactionDaoImpl();
        this.txProdDao = new TransactionProductDAOImpl();
    }

    public List<Transaction> getAllTransactions() {
        return txDao.findAll();
    }

    public Transaction getTxById(Long id) {
        return txDao.findById(id);
    }

    public Transaction saveTx(Transaction transaction) {
        return txDao.save(transaction);
    }

    public TransactionProduct saveTxProd(TransactionProduct txProd) {
        return txProdDao.saveTxProd(txProd);
    }

    public Transaction createTransaction(Customer customer, Set<LineItem> items) {
        List<LineItem> lineItems = new ArrayList<>();
        InventoryService inventoryService = new InventoryService();
        Long txTotal = 0L;
        for (LineItem lineItem : items) {
            Inventory inv = inventoryService.getInventoryByProductId(lineItem.getProduct().getId());
            if (inv.getAmount() - lineItem.getAmount() < 0) {
                System.out.println("Cant add product " + lineItem.getProduct().getName());
                continue;
            }
            txTotal += lineItem.getProduct().getPrice() * lineItem.getAmount();
            lineItems.add(lineItem);
        }
        if (!lineItems.isEmpty()) {
            return txDao.createTransaction(customer, lineItems, txTotal);
        }
        return null;
    }

    public void reportCustomerTx() {
        List<Object[]> results = txDao.countOrderPerUser();
        System.out.printf("%-20s %-20s %-15s%n", "Customer Name", "Order Count", "Total");
        System.out.println(StringUtils.repeat("-", 60));

        for (Object[] result : results) {
            System.out.printf("%-20s %-20s %-15s%n", result[0], result[1], result[2]);
        }

    }

}
