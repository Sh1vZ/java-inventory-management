package interace;

import entity.Transaction;

import java.util.List;

public interface TransactionDAO {

    Transaction findTxById(Long id);

    List<Transaction> findAll();

    Transaction saveTx(Transaction transaction);

}
