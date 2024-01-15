package interace;

import entity.Customer;
import entity.LineItem;
import entity.Transaction;
import entity.TransactionProduct;

import java.util.List;

public interface TransactionDAO {

    Transaction findTxById(Long id);

    List<Transaction> findAll();
    List<Object[]> countOrderPerUser(Long userId);

    Transaction saveTx(Transaction transaction);
    Transaction createTransaction(Customer customer, List<LineItem> items);

}
