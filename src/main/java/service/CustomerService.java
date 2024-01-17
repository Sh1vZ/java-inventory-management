package service;

import dao.CustomerDAOImpl;
import entity.Customer;
import interace.CustomerDAO;

import java.util.List;

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


    public List<Customer> findAllCustomers() {
        return customerDAO.findAll();
    }

    public void printCustomer(Customer customer) {
        System.out.printf("%-10s %-20s%n", customer.getId(), customer.getName());
    }

    public void findCustomerAndPrintout(Long id) {
        System.out.printf("%-10s %-20s%n", "Customer ID", "Customer Name");
        printCustomer(getCustomerById(id));
    }

    public int printAllCustomers() {
        List<Customer> customers = findAllCustomers();
        System.out.printf("%-10s %-20s%n", "Customer ID", "Customer Name");
        if(customers.isEmpty()){
            System.out.println("No customers found");
            return 0;
        }
        for (Customer customer : customers) {
            printCustomer(customer);
        }
        return customers.size();
    }

    public void deleteCustomer(Long id) {
        customerDAO.deleteByid(id);
    }

    public void updateCustomer(Customer customer) {
        customerDAO.update(customer);
    }

}
