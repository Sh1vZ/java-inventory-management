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
}
