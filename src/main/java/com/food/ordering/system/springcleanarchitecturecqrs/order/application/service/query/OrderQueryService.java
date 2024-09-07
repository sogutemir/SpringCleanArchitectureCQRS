package com.food.ordering.system.springcleanarchitecturecqrs.order.application.service.query;


import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.dto.query.OrderResponseDto;

import java.util.List;
import java.util.Optional;

public interface OrderQueryService {

    Optional<OrderResponseDto> findOrderById(Long id);

    List<OrderResponseDto> findOrdersByUserId(Long userId);
}