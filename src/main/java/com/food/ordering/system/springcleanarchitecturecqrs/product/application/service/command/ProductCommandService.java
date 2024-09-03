package com.food.ordering.system.springcleanarchitecturecqrs.product.application.service.command;

import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.dto.ProductDTO;

import java.util.Optional;

public interface ProductCommandService {

    ProductDTO createProduct(ProductDTO productDTO);

    Optional<ProductDTO> updateProduct(Long id, ProductDTO productDTO);

    void deleteProduct(Long id);
}