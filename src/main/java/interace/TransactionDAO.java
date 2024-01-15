package interace;

import entity.Transaction;

import java.util.List;

public interface TransactionDAO {

    Transaction findTxById(Long id);

    List<Transaction> findAll();
    List<Object[]> countOrderPerUser(Long userId);

    Transaction saveTx(Transaction transaction);

}
