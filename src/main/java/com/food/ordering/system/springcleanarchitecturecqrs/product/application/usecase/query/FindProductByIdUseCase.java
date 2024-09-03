package com.food.ordering.system.springcleanarchitecturecqrs.product.application.usecase.query;

import com.food.ordering.system.springcleanarchitecturecqrs.product.application.exception.ProductNotFoundException;
import com.food.ordering.system.springcleanarchitecturecqrs.product.dataaccess.adapter.ProductPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.dto.ProductResponseDTO;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.mapper.ProductResponseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class FindProductByIdUseCase {

    private final ProductPersistenceAdapter productPersistenceAdapter;

    public FindProductByIdUseCase(ProductPersistenceAdapter productPersistenceAdapter) {
        this.productPersistenceAdapter = productPersistenceAdapter;
    }

    public Optional<ProductResponseDTO> execute(Long id) {
        try {
            log.info("Finding product with id: {}", id);
            Optional<Product> product = productPersistenceAdapter.findById(id);
            if (product.isPresent()) {
                log.info("Product found with id: {}", id);
                return Optional.of(ProductResponseMapper.toDTO(product.get()));
            } else {
                throw new ProductNotFoundException(id);
            }
        } catch (ProductNotFoundException e) {
            log.error("Product with id {} not found.", id);
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while finding product with id: {}", id, e);
            throw e;
        }
    }
}