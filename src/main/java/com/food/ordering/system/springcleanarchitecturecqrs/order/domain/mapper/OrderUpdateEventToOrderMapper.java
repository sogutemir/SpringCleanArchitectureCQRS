package com.food.ordering.system.springcleanarchitecturecqrs.order.domain.mapper;

import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.enums.OrderStatus;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.event.OrderUpdateEvent;
import org.springframework.stereotype.Component;

@Component
public class OrderUpdateEventToOrderMapper {

    public static void updateOrderStatus(OrderUpdateEvent orderUpdateEvent, Order order) {
        if (orderUpdateEvent != null && order != null) {
            order.setOrderStatus(orderUpdateEvent.getStatus());
        }
    }

    public static OrderUpdateEvent toOrderUpdateEvent(Order order, OrderStatus status) {
        return OrderUpdateEvent.builder()
                .orderId(order.getId())
                .status(status)
                .build();
    }
}