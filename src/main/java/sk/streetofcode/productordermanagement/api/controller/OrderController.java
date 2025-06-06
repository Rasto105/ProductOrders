package sk.streetofcode.productordermanagement.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.streetofcode.productordermanagement.api.OrderService;
import sk.streetofcode.productordermanagement.api.request.AddOrderRequest;
import sk.streetofcode.productordermanagement.api.request.AddProductRequest;
import sk.streetofcode.productordermanagement.api.request.UpdateAmountProduct;
import sk.streetofcode.productordermanagement.api.request.UpdateStatusOrder;
import sk.streetofcode.productordermanagement.domain.Order;
import sk.streetofcode.productordermanagement.domain.Product;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
        @GetMapping("{id}")
        public ResponseEntity<Order> getById(@PathVariable("id")long id){
            return ResponseEntity.ok().body(orderService.get(id));
        }
        @GetMapping
        public ResponseEntity<List<Order>> getAll(@RequestParam(required = false)Long id){

            return ResponseEntity.ok().body(orderService.getAll());
        }
        @DeleteMapping("{id}")
        public ResponseEntity<Void> delete(@PathVariable("id")long id){
            orderService.delete(id);
            return ResponseEntity.ok().build();
        }
        @PostMapping
        public ResponseEntity<Long> add(@RequestBody AddOrderRequest request){
            return ResponseEntity.status(HttpStatus.CREATED).body(orderService.add(request));
        }
        @PutMapping("{id}/paid")
        public ResponseEntity<Void> updateStatus(@PathVariable("id") long id, @RequestBody UpdateStatusOrder request){
            orderService.changeStatus(id);
            return ResponseEntity.ok().build();
        }


}
