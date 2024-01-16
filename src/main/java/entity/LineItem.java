package entity;

public class LineItem {

    private final Product product;

    private final Long amount;

    public LineItem(Product product, Long amount) {
        this.product = product;
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public Long getAmount() {
        return amount;
    }
}
