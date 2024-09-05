package com.food.ordering.system.springcleanarchitecturecqrs.order.application.service.command;

import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.dto.OrderDto;

import java.util.Map;
import java.util.Optional;

public interface OrderCommandService {

    OrderDto createOrder(OrderDto orderDTO);

    Optional<OrderDto> updateOrder(Long id, OrderDto orderDTO);

    void deleteOrder(Long id);
}
