package com.food.ordering.system.springcleanarchitecturecqrs.order.domain.mapper;

import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.dto.OrderDTO;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;

import java.util.List;

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

    public static void partialUpdate(OrderDTO orderDTO, Order order) {
        if (orderDTO == null || order == null) {
            return;
        }
        if (orderDTO.getUserId() != null) {
            order.setUser(User.builder().id(orderDTO.getUserId()).build());
        }
        if (orderDTO.getTotalAmount() != null) {
            order.setTotalAmount(orderDTO.getTotalAmount());
        }
        if (orderDTO.getProductIds() != null) {
            // Şimdilik bu kısım boş bırakıldı clean architecture prensiplerine uygun olması için.
            // Sonradan ProductService çağırarak doldurulacak.
        }
    }
}