package com.food.ordering.system.springcleanarchitecturecqrs.product.domain.mapper;


import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.dto.ProductResponseDto;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;

public class ProductResponseMapper {

    public static ProductResponseDto toDTO(Product product) {
        if (product == null) {
            return null;
        }
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .productStatus(product.getProductStatus().name())
                .build();
    }
}