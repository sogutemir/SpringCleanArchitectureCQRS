package com.food.ordering.system.springcleanarchitecturecqrs.product.application.usecase.command;

import com.food.ordering.system.springcleanarchitecturecqrs.product.dataaccess.adapter.ProductPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.dto.crud.ProductDto;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Component
public class CreateProductUseCase {

    private final ProductPersistenceAdapter productPersistenceAdapter;

    public CreateProductUseCase(ProductPersistenceAdapter productPersistenceAdapter) {
        this.productPersistenceAdapter = productPersistenceAdapter;
    }

    public ProductDto execute(ProductDto productDTO) {
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
