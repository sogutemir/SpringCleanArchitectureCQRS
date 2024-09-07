package com.food.ordering.system.springcleanarchitecturecqrs.order.application.mapper;

import com.food.ordering.system.springcleanarchitecturecqrs.order.application.dto.crud.OrderDto;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;
import java.util.List;
import java.util.Map;

public class OrderMapper {

    public static Order toEntity(OrderDto orderDTO, List<Product> products) {
        if (orderDTO == null) {
            return null;
        }
        return Order.builder()
                .user(User.builder().id(orderDTO.getUserId()).build())
                .totalAmount(orderDTO.getTotalAmount())
                .products(products)
                .build();
    }

    public static OrderDto toDTO(Order order, Map<Long, Integer> productQuantities) {
        if (order == null) {
            return null;
        }
        return OrderDto.builder()
                .userId(order.getUser().getId())
                .totalAmount(order.getTotalAmount())
                .productQuantities(productQuantities)
                .build();
    }
}
