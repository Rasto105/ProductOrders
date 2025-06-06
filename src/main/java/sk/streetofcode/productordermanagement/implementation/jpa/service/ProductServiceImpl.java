package sk.streetofcode.productordermanagement.implementation.jpa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import sk.streetofcode.productordermanagement.api.exception.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import sk.streetofcode.productordermanagement.api.ProductService;
import sk.streetofcode.productordermanagement.api.exception.InternalErrorException;
import sk.streetofcode.productordermanagement.api.exception.ResourceNotFoundException;
import sk.streetofcode.productordermanagement.api.request.AddProductRequest;
import sk.streetofcode.productordermanagement.api.request.UpdateAmountProduct;
import sk.streetofcode.productordermanagement.api.request.UpdateProductRequest;
import sk.streetofcode.productordermanagement.domain.Product;
import sk.streetofcode.productordermanagement.implementation.jpa.entity.ProductEntity;
import sk.streetofcode.productordermanagement.implementation.jpa.repository.ProductRepository;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product get(long id) {
        return repository.
                findById(id)
                .map(this::mapProductEntityToProduct)
                .orElseThrow(()->new ResourceNotFoundException("Product with id " + id + " not found"));
    }

    @Override
    public List<Product> getAll() {
        return repository.findAll().stream().map(this::mapProductEntityToProduct).toList();
    }

    @Override
    public long getAmount(long id) {
        return repository.findById(id)
                .map(this::mapProductEntityToAmount) // ak produkt existuje, vráti amount
                .orElseThrow(()->new ResourceNotFoundException("Product with id " + id + " not found"));
    }

    @Override
    public void delete(long id) {
        if (this.get(id) != null){
            repository.deleteById(id);
        }
    }

    @Override
    public long add(AddProductRequest request) {
        try{
            return repository.save(new ProductEntity(request.getName(), request.getDescription(), request.getAmount(), request.getPrice())).getId();
        }catch (DataIntegrityViolationException e){
            throw new BadRequestException("Product with name "+request.getName() + " already exists");
        }catch (DataAccessException e){
            logger.error("Error while adding product", e);
            throw new InternalErrorException("Error while adding product");
        }
    }

    @Override
    public void edit(long id, UpdateProductRequest request) {
        final ProductEntity productEntity = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " was not found"));

        productEntity.setName(request.getName());
        productEntity.setDescription(request.getDescription());
        repository.save(productEntity);
    }

    @Override
    public void editAmount(long id, UpdateAmountProduct request) {
        final ProductEntity productEntity = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " was not found"));
        productEntity.setAmount(request.getAmount());
        repository.save(productEntity);
    }
    private Product mapProductEntityToProduct(ProductEntity entity){
        return new Product(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getAmount(),
                entity.getPrice()
        );
    }
    private long mapProductEntityToAmount(ProductEntity entity){
        return entity.getAmount();
    }
}
