package dto.transaction;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CreateTransactionDTO {

    @PositiveOrZero(message = "Amount must be a whole number")
    @NotNull(message = "amount must not be null")
    private Long customerId;
    @Valid
    @Size(min = 1, message = "At least one item is required")
    @NotNull(message = "items must not be null")
    private List<LineItemDTO> lineItems;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<LineItemDTO> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItemDTO> lineItems) {
        this.lineItems = lineItems;
    }


}
