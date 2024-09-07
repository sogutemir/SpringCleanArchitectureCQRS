package com.food.ordering.system.springcleanarchitecturecqrs.product.application.usecase.query;

import com.food.ordering.system.springcleanarchitecturecqrs.product.dataaccess.adapter.ProductPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.dto.query.ProductResponseDto;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.mapper.ProductResponseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class FindProductsByNameUseCase {

    private final ProductPersistenceAdapter productPersistenceAdapter;

    public FindProductsByNameUseCase(ProductPersistenceAdapter productPersistenceAdapter) {
        this.productPersistenceAdapter = productPersistenceAdapter;
    }

    public List<ProductResponseDto> execute(String name) {
        try {
            log.info("Finding products with name containing: {}", name);
            List<Product> products = productPersistenceAdapter.findByNameContaining(name);
            return products.stream()
                    .map(ProductResponseMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error occurred while finding products with name containing: {}", name, e);
            throw e;
        }
    }
}