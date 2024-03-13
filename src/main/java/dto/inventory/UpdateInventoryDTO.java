package dto.inventory;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class UpdateInventoryDTO {
    @PositiveOrZero(message = "Amount must be a whole number")
    @NotNull(message = "amount must not be null")
    private Long amount;

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
