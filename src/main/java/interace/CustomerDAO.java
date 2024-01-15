package interace;

import entity.Customer;

public interface CustomerDAO {
    Customer saveCustomer(Customer customer);

    Customer getCustomerById(Long id);

    void updateCustomer(Customer customer);

    void deleteCustomer(Long id);
}
