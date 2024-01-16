package service;

import dao.CustomerDAOImpl;
import entity.Customer;
import interace.CustomerDAO;

public class CustomerService {
    private final CustomerDAO customerDAO;

    public CustomerService() {
        this.customerDAO = new CustomerDAOImpl();
    }

    public Customer createCustomer(Customer customer) {
        return customerDAO.save(customer);
    }

    public Customer getCustomerById(Long id) {
        return customerDAO.findById(id);
    }


    public void deleteCustomer(Long id) {
        customerDAO.deleteByid(id);
    }

    public void updateCustomer(Customer customer) {
        customerDAO.update(customer);
    }

}
