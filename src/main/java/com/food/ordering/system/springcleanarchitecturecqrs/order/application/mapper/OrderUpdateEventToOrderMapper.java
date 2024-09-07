package com.food.ordering.system.springcleanarchitecturecqrs.order.application.mapper;

import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.enums.OrderStatus;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.dto.event.OrderUpdateEventDto;
import org.springframework.stereotype.Component;

@Component
public class OrderUpdateEventToOrderMapper {

    public static void updateOrderStatus(OrderUpdateEventDto orderUpdateEventDto, Order order) {
        if (orderUpdateEventDto != null && order != null) {
            order.setOrderStatus(orderUpdateEventDto.getStatus());
        }
    }

    public static OrderUpdateEventDto toOrderUpdateEvent(Order order, OrderStatus status) {
        return OrderUpdateEventDto.builder()
                .orderId(order.getId())
                .status(status)
                .build();
    }
}