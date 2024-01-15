package service;

import dao.ProductDAOImpl;
import dao.TransactionDaoImpl;
import dao.TransactionProductDAOImpl;
import entity.Transaction;
import entity.TransactionProduct;
import interace.ProductDAO;
import interace.TransactionDAO;
import interace.TransactionProductDAO;

import java.util.List;

public class TransactionService {
    private TransactionDAO txDao;
    private TransactionProductDAO txProdDao;

    public TransactionService() {
        this.txDao = new TransactionDaoImpl();
        this.txProdDao = new TransactionProductDAOImpl();
    }

    public List<Transaction> getAllTransactions() {
        return txDao.findAll();
    }

    public Transaction saveTx(Transaction transaction) {
        return  txDao.saveTx(transaction);
    }
    public TransactionProduct saveTxProd(TransactionProduct txProd) {
        return txProdDao.saveTxProd(txProd);
    }

    public void reportUserTx(Long userId){
        List<Object[]> results = txDao.countOrderPerUser(userId);
        System.out.printf("%-20s %-15s%n", "Customer Name", "Order Count");
        System.out.println("------------------------------");

        for (Object[] result : results) {
            System.out.printf("%-20s %-15s%n", result[0], result[1]);
        }

    }

}
