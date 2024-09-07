package com.food.ordering.system.springcleanarchitecturecqrs.product.application.mapper;

import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.dto.event.ProductNotificationEventDto;

public class ProductNotificationEventMapper {

    public static ProductNotificationEventDto toEvent(Product product, Integer quantity, String message) {
        if (product == null || product.getOrders() == null || product.getOrders().isEmpty()) {
            return null;
        }

        return ProductNotificationEventDto.builder()
                .orderId(product.getOrders().get(0).getId())
                .userId(product.getOrders().get(0).getUser().getId())
                .userName(product.getOrders().get(0).getUser().getName())
                .productId(product.getId())
                .message(message)
                .quantity(quantity)
                .build();
    }

    public static ProductNotificationEventDto toEvent(Product product, Integer quantity) {
        if (product == null || product.getOrders() == null || product.getOrders().isEmpty()) {
            return null;
        }

        return ProductNotificationEventDto.builder()
                .orderId(product.getOrders().get(0).getId())
                .userId(product.getOrders().get(0).getUser().getId())
                .userName(product.getOrders().get(0).getUser().getName())
                .productId(product.getId())
                .message("")
                .quantity(quantity)
                .build();
    }
}