package sk.streetofcode.productordermanagement.api;

import sk.streetofcode.productordermanagement.api.request.AddOrderRequest;
import sk.streetofcode.productordermanagement.domain.Order;

import java.util.List;

public interface OrderService {
    Order get(long id);
    List<Order> getAll();
    long add(AddOrderRequest request);
    void delete(long id);
    void update(long id);
    void changeStatus(long id);
}
