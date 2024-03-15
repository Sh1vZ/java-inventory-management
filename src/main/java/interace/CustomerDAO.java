package interace;

import entity.Customer;

public interface CustomerDAO extends BaseDAO<Customer, Long> {
    Long countAllCustomers();

}
