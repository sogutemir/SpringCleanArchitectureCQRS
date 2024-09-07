package com.food.ordering.system.springcleanarchitecturecqrs.product.application.service.command;

import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.dto.crud.ProductDto;

import java.util.Optional;

public interface ProductCommandService {

    ProductDto createProduct(ProductDto productDTO);

    Optional<ProductDto> updateProduct(Long id, ProductDto productDTO);

    void deleteProduct(Long id);
}