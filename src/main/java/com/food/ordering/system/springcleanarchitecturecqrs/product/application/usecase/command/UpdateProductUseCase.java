package com.food.ordering.system.springcleanarchitecturecqrs.product.application.usecase.command;

import com.food.ordering.system.springcleanarchitecturecqrs.product.application.exception.ProductNotFoundException;
import com.food.ordering.system.springcleanarchitecturecqrs.product.dataaccess.adapter.ProductPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.dto.crud.ProductDto;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Transactional
@Component
public class UpdateProductUseCase {

    private final ProductPersistenceAdapter productPersistenceAdapter;

    public UpdateProductUseCase(ProductPersistenceAdapter productPersistenceAdapter) {
        this.productPersistenceAdapter = productPersistenceAdapter;
    }

    public Optional<ProductDto> execute(Long id, ProductDto productDTO) {
        try {
            log.info("Updating product with id: {}", id);
            Optional<Product> existingProduct = productPersistenceAdapter.findById(id);

            if (existingProduct.isPresent()) {
                Product product = existingProduct.get();
                ProductMapper.partialUpdate(productDTO, product);
                Product updatedProduct = productPersistenceAdapter.save(product);
                log.info("Product updated successfully with id: {}", updatedProduct.getId());
                return Optional.of(ProductMapper.toDTO(updatedProduct));
            } else {
                throw new ProductNotFoundException(id);
            }
        } catch (ProductNotFoundException e) {
            log.error("Product with id {} not found.", id);
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while updating product with id: {}", id, e);
            throw e;
        }
    }
}
