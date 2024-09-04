package com.food.ordering.system.springcleanarchitecturecqrs.order.domain.mapper;

import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.dto.OrderDTO;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderMapper {

    public static Order toEntity(OrderDTO orderDTO, List<Product> products) {
        if (orderDTO == null) {
            return null;
        }
        return Order.builder()
                .user(User.builder().id(orderDTO.getUserId()).build())
                .totalAmount(orderDTO.getTotalAmount())
                .products(products)
                .build();
    }

    public static Order toEntity(OrderDTO orderDTO, Map<Long, Integer> productIdQuantityMap, BigDecimal totalAmount) {
        if (orderDTO == null || productIdQuantityMap == null) {
            return null;
        }

        List<Product> products = productIdQuantityMap.keySet().stream()
                .map(id -> Product.builder().id(id).build())
                .collect(Collectors.toList());

        return Order.builder()
                .user(User.builder().id(orderDTO.getUserId()).build())
                .totalAmount(totalAmount)
                .products(products)
                .build();
    }

    public static OrderDTO toDTO(Order order) {
        if (order == null) {
            return null;
        }
        return OrderDTO.builder()
                .userId(order.getUser().getId())
                .totalAmount(order.getTotalAmount())
                .productIds(order.getProducts() != null ?
                        order.getProducts().stream()
                                .map(Product::getId)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    public static void partialUpdate(OrderDTO orderDTO, Order order, List<Product> products) {
        if (orderDTO == null || order == null) {
            return;
        }
        if (orderDTO.getUserId() != null) {
            order.setUser(User.builder().id(orderDTO.getUserId()).build());
        }
        if (orderDTO.getTotalAmount() != null) {
            order.setTotalAmount(orderDTO.getTotalAmount());
        }
        if (orderDTO.getProductIds() != null && products != null) {
            order.setProducts(products);
        }
    }
}