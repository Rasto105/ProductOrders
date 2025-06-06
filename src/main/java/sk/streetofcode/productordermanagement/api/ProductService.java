package sk.streetofcode.productordermanagement.api;

import sk.streetofcode.productordermanagement.api.request.AddProductRequest;
import sk.streetofcode.productordermanagement.api.request.UpdateAmountProduct;
import sk.streetofcode.productordermanagement.api.request.UpdateProductRequest;
import sk.streetofcode.productordermanagement.domain.Product;

import java.util.List;

public interface ProductService {
        Product get(long id);
        List<Product> getAll();
        long getAmount(long id);
        void delete(long id);
        long add(AddProductRequest request);
        void edit(long id, UpdateProductRequest request);
        void editAmount(long id, UpdateAmountProduct request);


}
