package com.food.ordering.system.springcleanarchitecturecqrs.product.application.service.query;

import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.dto.ProductResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ProductQueryService {

    Optional<ProductResponseDTO> findProductById(Long id);

    List<ProductResponseDTO> findProductsByName(String name);
}