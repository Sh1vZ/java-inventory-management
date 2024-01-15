package service;

import dao.CustomerDAOImpl;
import entity.Customer;
import interace.CustomerDAO;

public class CustomerService {
    private CustomerDAO  customerDAO;

    public CustomerService() {
        this.customerDAO = new CustomerDAOImpl();
    }

    public Customer createCustomer(Customer customer){
        return customerDAO.saveCustomer(customer);
    }

    public Customer getCustomerById(Long id){
        return customerDAO.getCustomerById(id);
    }


    public void deleteCustomer(Long id){
        customerDAO.deleteCustomer(id);
    }

    public void updateCustomer(Customer customer){
        customerDAO.updateCustomer(customer);
    }

}
