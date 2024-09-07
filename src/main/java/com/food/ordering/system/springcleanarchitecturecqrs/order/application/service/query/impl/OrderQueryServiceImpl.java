package com.food.ordering.system.springcleanarchitecturecqrs.order.application.service.query.impl;

import com.food.ordering.system.springcleanarchitecturecqrs.order.application.service.query.OrderQueryService;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.query.FindOrderByIdUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.query.FindOrdersByUserIdUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.dto.query.OrderResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OrderQueryServiceImpl implements OrderQueryService {

    private final FindOrderByIdUseCase findOrderByIdUseCase;
    private final FindOrdersByUserIdUseCase findOrdersByUserIdUseCase;

    public OrderQueryServiceImpl(
            FindOrderByIdUseCase findOrderByIdUseCase,
            FindOrdersByUserIdUseCase findOrdersByUserIdUseCase) {
        this.findOrderByIdUseCase = findOrderByIdUseCase;
        this.findOrdersByUserIdUseCase = findOrdersByUserIdUseCase;
    }

    @Override
    public Optional<OrderResponseDto> findOrderById(Long id) {
        log.info("Finding order with id: {}", id);
        return findOrderByIdUseCase.execute(id);
    }

    @Override
    public List<OrderResponseDto> findOrdersByUserId(Long userId) {
        log.info("Finding orders for user with id: {}", userId);
        return findOrdersByUserIdUseCase.execute(userId);
    }
}