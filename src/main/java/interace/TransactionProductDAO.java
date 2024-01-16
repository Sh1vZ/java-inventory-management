package interace;

import entity.TransactionProduct;

public interface TransactionProductDAO extends BaseDAO<TransactionDAO, Long> {
    TransactionProduct saveTxProd(TransactionProduct txProd);
}
