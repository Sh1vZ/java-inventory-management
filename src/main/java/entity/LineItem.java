package entity;

public class LineItem {

    private Product product;

    private Long amount;

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
