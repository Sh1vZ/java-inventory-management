package dto.customer;

import dto.transaction.CustomerTransactionDTO;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.List;

public class CustomerDTO {

    private Long id;

    @NotBlank(message = "Name must not be blank")
    private String name;
    private List<CustomerTransactionDTO> transactions;
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    public List<CustomerTransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<CustomerTransactionDTO> transactions) {
        this.transactions = transactions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
