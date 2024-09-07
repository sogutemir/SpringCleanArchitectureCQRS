package com.food.ordering.system.springcleanarchitecturecqrs.order.application.mapper;


import com.food.ordering.system.springcleanarchitecturecqrs.order.application.dto.query.OrderResponseDto;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.mapper.ProductResponseMapper;
import java.util.stream.Collectors;

public class OrderResponseMapper {

    public static OrderResponseDto toDTO(Order order) {
        if (order == null) {
            return null;
        }
        return OrderResponseDto.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus().name())
                .products(order.getProducts() != null ?
                        order.getProducts().stream()
                                .map(ProductResponseMapper::toDTO)
                                .collect(Collectors.toList()) : null)
                .build();
    }
}