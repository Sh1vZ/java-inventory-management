package entity;

import builder.ProductBuilder;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "supplier")
    private String supplier;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "sku", nullable = false)
    private String sku;

    @Column(name = "color")
    private String color;

    @Column(name = "size")
    private String size;

    @Column(name = "type")
    private String type;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    public Product() {
    }

    public static ProductBuilder builder() {
        return ProductBuilder.create();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSupplier() {
        return supplier;
    }

    public Long getPrice() {
        return price;
    }

    public String getSku() {
        return sku;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setType(String type) {
        this.type = type;
    }
}

