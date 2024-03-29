package service;

import dao.CustomerDAOImpl;
import entity.Customer;
import interace.CustomerDAO;
import iterator.GenericIterator;

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

    public Long countAllCustomers() {
        return customerDAO.countAllCustomers();
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
        GenericIterator<Customer> it = new GenericIterator<>(customers);

        while (it.hasNext()) {
            Customer customer = it.next();
            printCustomer(customer);
        }
//        for (Customer customer : customers) {
//            printCustomer(customer);
//        }
        return customers.size();
    }

    public Customer deleteCustomer(Long id) {
        return customerDAO.deleteByid(id);
    }

    public Customer updateCustomer(Customer customer) {
        return customerDAO.update(customer);
    }

}
