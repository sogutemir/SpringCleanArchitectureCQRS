package com.food.ordering.system.springcleanarchitecturecqrs.product.application.usecase.crud;

import com.food.ordering.system.springcleanarchitecturecqrs.product.application.exception.ProductNotFoundException;
import com.food.ordering.system.springcleanarchitecturecqrs.product.dataaccess.adapter.ProductPersistenceAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeleteProductUseCase {

    private final ProductPersistenceAdapter productPersistenceAdapter;

    public DeleteProductUseCase(ProductPersistenceAdapter productPersistenceAdapter) {
        this.productPersistenceAdapter = productPersistenceAdapter;
    }

    public void execute(Long id) {
        try {
            log.info("Deleting product with id: {}", id);
            if (productPersistenceAdapter.findById(id).isPresent()) {
                productPersistenceAdapter.deleteById(id);
                log.info("Product deleted successfully with id: {}", id);
            } else {
                throw new ProductNotFoundException(id);
            }
        } catch (ProductNotFoundException e) {
            log.error("Product with id {} not found.", id);
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while deleting product with id: {}", id, e);
            throw e;
        }
    }
}