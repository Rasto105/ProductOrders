package sk.streetofcode.productordermanagement.implementation.jpa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sk.streetofcode.productordermanagement.api.OrderService;
import sk.streetofcode.productordermanagement.api.exception.ResourceNotFoundException;
import sk.streetofcode.productordermanagement.api.request.AddOrderRequest;
import sk.streetofcode.productordermanagement.domain.Order;
import sk.streetofcode.productordermanagement.implementation.jpa.entity.OrderEntity;
import sk.streetofcode.productordermanagement.implementation.jpa.entity.ProductEntity;
import sk.streetofcode.productordermanagement.implementation.jpa.repository.OrderRepository;
import sk.streetofcode.productordermanagement.implementation.jpa.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final ProductRepository productRepository;
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    public OrderServiceImpl(OrderRepository repository, ProductRepository productRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
    }

    @Override
    public Order get(long id) {

        return repository.
                findById(id)
                .map(this::mapOrderEntityToOrder)
                .orElseThrow(()->new ResourceNotFoundException("Order with id " + id + " not found"));
    }

    @Override
    public List<Order> getAll() {

        return repository.findAll().stream().map(this::mapOrderEntityToOrder).toList();
    }

    @Override
    public long add(AddOrderRequest request) {

        ProductEntity productEntity = productRepository.findById(request.getShoppingList().getProductId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product with id " + request.getShoppingList().getProductId() + " not found"
                ));

        if (productEntity.getAmount() < request.getShoppingList().getAmount()) {
            throw new IllegalArgumentException("Not enough products in stock!");
        }
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setProduct(productEntity);
        orderEntity.setAmount(request.getShoppingList().getAmount());
        orderEntity.setTotalPrice(
                productEntity.getPrice()*request.getShoppingList().getAmount());
         orderEntity.setPaid(false);
        orderEntity.setCreatedAt(OffsetDateTime.now());

        // update amount in product table
        productEntity.setAmount(productEntity.getAmount() - request.getShoppingList().getAmount());
        productRepository.save(productEntity);

        OrderEntity savedOrder = repository.save(orderEntity);
        return savedOrder.getId();
    }

    @Override
    public void delete(long id) {
        if (this.get(id) != null){
            repository.deleteById(id);
        }

    }

    @Override
    public void update(long id) {
        final OrderEntity orderEntity = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("order with id: "+ id + " was not found"));

    }

    @Override
    public void changeStatus(long id) {
        final OrderEntity orderEntity = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("order with id: "+ id + " was not found"));
        orderEntity.setPaid(true);
        repository.save(orderEntity);
    }
    private Order mapOrderEntityToOrder(OrderEntity entity){
        return new Order(
                entity.getId(),
                entity.getProduct().getId(),
                entity.isPaid(),
                entity.getAmount(),
                entity.getTotalPrice(),
                entity.getCreatedAt()
        );
    }
}
