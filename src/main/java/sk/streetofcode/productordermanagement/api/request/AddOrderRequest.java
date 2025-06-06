package sk.streetofcode.productordermanagement.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sk.streetofcode.productordermanagement.domain.OrderItems;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddOrderRequest {
    private OrderItems shoppingList;
}
