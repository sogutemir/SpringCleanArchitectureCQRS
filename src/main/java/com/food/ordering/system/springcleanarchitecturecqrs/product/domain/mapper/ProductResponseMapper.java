package com.food.ordering.system.springcleanarchitecturecqrs.product.domain.mapper;


import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.dto.ProductResponseDTO;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;

public class ProductResponseMapper {

    public static ProductResponseDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .productStatus(product.getProductStatus().name())
                .build();
    }
}