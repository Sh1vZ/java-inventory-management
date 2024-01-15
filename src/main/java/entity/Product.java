package entity;

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

    private Product() {
    }

    public static Builder builder() {
        return new Builder();
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

    public static class Builder {
        private final Product product = new Product();

        private Builder() {
        }

        public Builder name(String name) {
            product.name = name;
            return this;
        }

        public Builder supplier(String supplier) {
            product.supplier = supplier;
            return this;
        }

        public Builder price(Long price) {
            product.price = price;
            return this;
        }

        public Builder sku(String sku) {
            product.sku = sku;
            return this;
        }

        public Builder color(String color) {
            product.color = color;
            return this;
        }

        public Builder size(String size) {
            product.size = size;
            return this;
        }

        public Builder type(String type) {
            product.type = type;
            return this;
        }

        public Product build() {
            return product;
        }
    }

}

