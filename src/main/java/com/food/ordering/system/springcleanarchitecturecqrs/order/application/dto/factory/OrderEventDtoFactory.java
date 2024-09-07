package com.food.ordering.system.springcleanarchitecturecqrs.order.application.dto.factory;

import com.food.ordering.system.springcleanarchitecturecqrs.order.application.dto.event.OrderCreateEventDto;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;

import java.util.Map;

public class OrderEventDtoFactory {

    public static OrderCreateEventDto createOrderEvent(Order order, Map<Long, Integer> productIdQuantityMap) {
        return new OrderCreateEventDto(
                order.getId(),
                order.getTotalAmount(),
                order.getUser().getId(),
                productIdQuantityMap
        );
    }
}