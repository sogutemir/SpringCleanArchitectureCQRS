package com.food.ordering.system.springcleanarchitecturecqrs.product.application.usecase.crud;

import com.food.ordering.system.springcleanarchitecturecqrs.product.dataaccess.adapter.ProductPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.dto.ProductDTO;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateProductUseCase {

    private final ProductPersistenceAdapter productPersistenceAdapter;

    public CreateProductUseCase(ProductPersistenceAdapter productPersistenceAdapter) {
        this.productPersistenceAdapter = productPersistenceAdapter;
    }

    public ProductDTO execute(ProductDTO productDTO) {
        try {
            log.info("Creating product with name: {}", productDTO.getName());
            Product product = ProductMapper.toEntity(productDTO);
            Product savedProduct = productPersistenceAdapter.save(product);
            log.info("Product created successfully with id: {}", savedProduct.getId());
            return ProductMapper.toDTO(savedProduct);
        } catch (Exception e) {
            log.error("Error occurred while creating product with name: {}", productDTO.getName(), e);
            throw e;
        }
    }

}
