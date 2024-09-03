package com.food.ordering.system.springcleanarchitecturecqrs.order.domain.mapper;


import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.dto.OrderResponseDTO;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.mapper.ProductResponseMapper;
import java.util.stream.Collectors;

public class OrderResponseMapper {

    public static OrderResponseDTO toDTO(Order order) {
        if (order == null) {
            return null;
        }
        return OrderResponseDTO.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus().name())
                .products(order.getProducts() != null ? order.getProducts().stream().map(ProductResponseMapper::toDTO).collect(Collectors.toList()) : null)
                .build();
    }
}