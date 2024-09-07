package com.food.ordering.system.springcleanarchitecturecqrs.product.application.service.query;

import com.food.ordering.system.springcleanarchitecturecqrs.product.application.dto.query.ProductResponseDto;

import java.util.List;
import java.util.Optional;

public interface ProductQueryService {

    Optional<ProductResponseDto> findProductById(Long id);

    List<ProductResponseDto> findProductsByName(String name);
}