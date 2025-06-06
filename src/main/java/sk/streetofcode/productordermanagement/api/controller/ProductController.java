package sk.streetofcode.productordermanagement.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.streetofcode.productordermanagement.api.ProductService;
import sk.streetofcode.productordermanagement.api.request.AddOrderRequest;
import sk.streetofcode.productordermanagement.api.request.AddProductRequest;
import sk.streetofcode.productordermanagement.api.request.UpdateAmountProduct;
import sk.streetofcode.productordermanagement.api.request.UpdateProductRequest;
import sk.streetofcode.productordermanagement.domain.Product;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("{id}/amount")
    public ResponseEntity<Long> getAmountById(@PathVariable("id")long id){
        return ResponseEntity.ok().body(productService.getAmount(id));
    }
    @GetMapping("{id}")
    @Operation(
            summary = "Get product by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Product not found"
                    )
            }
    )
    public ResponseEntity<Product> getById(@PathVariable("id")long id){
        return ResponseEntity.ok().body(productService.get(id));
    }
    @GetMapping
    public ResponseEntity<List<Product>> getAll(@RequestParam(required = false)Long id){

        return ResponseEntity.ok().body(productService.getAll());
    }
    @PostMapping
    public ResponseEntity<Long> add(@RequestBody AddProductRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.add(request));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id")long id){
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable("id") long id, @RequestBody UpdateProductRequest request){
        productService.edit(id, request);
        return ResponseEntity.ok().build();
    }
    @PutMapping("{id}/amount")
    public ResponseEntity<Void> updateAmount(@PathVariable("id") long id, @RequestBody UpdateAmountProduct request){
        productService.editAmount(id, request);
        return ResponseEntity.ok().build();
    }
}
