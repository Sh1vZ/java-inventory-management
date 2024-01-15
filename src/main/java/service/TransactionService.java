package service;

import dao.ProductDAOImpl;
import dao.TransactionDaoImpl;
import dao.TransactionProductDAOImpl;
import entity.*;
import interace.ProductDAO;
import interace.TransactionDAO;
import interace.TransactionProductDAO;

import java.util.ArrayList;
import java.util.List;

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

    public Transaction saveTx(Transaction transaction) {
        return txDao.saveTx(transaction);
    }

    public TransactionProduct saveTxProd(TransactionProduct txProd) {
        return txProdDao.saveTxProd(txProd);
    }

    public void createTransaction(Customer customer, List<LineItem> items) {
        List<LineItem> lineItems = new ArrayList<>();
        InventoryService inventoryService = new InventoryService();
        Long txTotal=0L;
        for (LineItem lineItem : items) {
            Inventory inv = inventoryService.getInventoryByProductId(lineItem.getProduct().getId());
            if (inv.getAmount() - lineItem.getAmount() < 0) {
                System.out.println("Cant add product " + lineItem.getProduct().getName());
                continue;
            }
            txTotal+=lineItem.getProduct().getPrice() * lineItem.getAmount();
            lineItems.add(lineItem);
        }
        if (!lineItems.isEmpty()) {
            txDao.createTransaction(customer, lineItems,txTotal);
        }
    }

    public void reportUserTx(Long userId) {
        List<Object[]> results = txDao.countOrderPerUser(userId);
        System.out.printf("%-20s %-15s%n", "Customer Name", "Order Count");
        System.out.println("------------------------------");

        for (Object[] result : results) {
            System.out.printf("%-20s %-15s%n", result[0], result[1]);
        }

    }

}
