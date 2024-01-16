package builder;

import entity.Product;

// ProductBuilder.java
public class ProductBuilder {
    private final Product product = new Product();

    private ProductBuilder() {
    }

    public static ProductBuilder create() {
        return new ProductBuilder();
    }

    public ProductBuilder name(String name) {
        product.setName(name);
        return this;
    }

    public ProductBuilder supplier(String supplier) {
        product.setSupplier(supplier);
        return this;
    }

    public ProductBuilder price(Long price) {
        product.setPrice(price);
        return this;
    }

    public ProductBuilder sku(String sku) {
        product.setSku(sku);
        return this;
    }

    public ProductBuilder color(String color) {
        product.setColor(color);
        return this;
    }

    public ProductBuilder size(String size) {
        product.setSize(size);
        return this;
    }

    public ProductBuilder type(String type) {
        product.setType(type);
        return this;
    }

    public Product build() {
        return product;
    }
}
