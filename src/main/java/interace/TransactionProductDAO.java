package interace;

import entity.Transaction;
import entity.TransactionProduct;

public interface TransactionProductDAO {
    TransactionProduct saveTxProd(TransactionProduct txProd);

}
