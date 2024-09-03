package com.food.ordering.system.springcleanarchitecturecqrs.product.domain.mapper;


import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.dto.ProductDTO;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;

public class ProductMapper {

    public static Product toEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }
        return Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .stockQuantity(productDTO.getStockQuantity())
                .build();
    }

    public static void partialUpdate(ProductDTO productDTO, Product product) {
        if (productDTO == null || product == null) {
            return;
        }
        if (productDTO.getName() != null) {
            product.setName(productDTO.getName());
        }
        if (productDTO.getDescription() != null) {
            product.setDescription(productDTO.getDescription());
        }
        if (productDTO.getPrice() != null) {
            product.setPrice(productDTO.getPrice());
        }
        if (productDTO.getStockQuantity() != null) {
            product.setStockQuantity(productDTO.getStockQuantity());
        }
    }
}