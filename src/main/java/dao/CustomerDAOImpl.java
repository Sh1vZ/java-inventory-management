package dao;

import entity.Customer;
import interace.CustomerDAO;

public class CustomerDAOImpl extends BaseDaoImpl<Customer, Long> implements CustomerDAO {

    public CustomerDAOImpl() {
        super(Customer.class);
    }
}
