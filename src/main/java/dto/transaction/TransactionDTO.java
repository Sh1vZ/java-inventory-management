package dto.transaction;

import dto.customer.CustomerDTO;

import java.util.Date;
import java.util.Set;

public class TransactionDTO {

    private Long id;

    private Long total;


    private Set<TransactionProductDTO> transactionProducts;

    private CustomerDTO customer;

    private Date createdAt;


    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Set<TransactionProductDTO> getTransactionProducts() {
        return transactionProducts;
    }

    public void setTransactionProducts(Set<TransactionProductDTO> transactionProducts) {
        this.transactionProducts = transactionProducts;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
