package interace;

import entity.Customer;
import entity.LineItem;
import entity.Transaction;

import java.util.List;

public interface TransactionDAO extends BaseDAO<Transaction, Long> {

    List<Transaction> findAll();

    List<Object[]> countOrderPerUser();

    Transaction createTransaction(Customer customer, List<LineItem> items, Long txTotal);

}
