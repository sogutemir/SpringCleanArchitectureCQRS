package com.food.ordering.system.springcleanarchitecturecqrs.order.application.service.command;

import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.dto.OrderDTO;

import java.util.Map;
import java.util.Optional;

public interface OrderCommandService {

    OrderDTO createOrder(OrderDTO orderDTO, Map<String, String> productIdQuantityMap);

    Optional<OrderDTO> updateOrder(Long id, OrderDTO orderDTO, Map<String, String> productIdQuantityMap);

    void deleteOrder(Long id);
}