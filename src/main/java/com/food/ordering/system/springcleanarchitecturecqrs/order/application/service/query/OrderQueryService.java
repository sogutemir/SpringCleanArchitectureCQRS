package com.food.ordering.system.springcleanarchitecturecqrs.order.application.service.query;


import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.dto.OrderResponseDTO;

import java.util.List;
import java.util.Optional;

public interface OrderQueryService {

    Optional<OrderResponseDTO> findOrderById(Long id);

    List<OrderResponseDTO> findOrdersByUserId(Long userId);
}