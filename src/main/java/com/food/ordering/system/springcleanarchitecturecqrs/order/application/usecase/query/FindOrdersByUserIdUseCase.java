package com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.query;

import com.food.ordering.system.springcleanarchitecturecqrs.order.dataaccess.adapter.OrderPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.dto.OrderResponseDto;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.mapper.OrderResponseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class FindOrdersByUserIdUseCase {

    private final OrderPersistenceAdapter orderPersistenceAdapter;

    public FindOrdersByUserIdUseCase(OrderPersistenceAdapter orderPersistenceAdapter) {
        this.orderPersistenceAdapter = orderPersistenceAdapter;
    }

    public List<OrderResponseDto> execute(Long userId) {
        try {
            log.info("Finding orders for user with id: {}", userId);
            List<Order> orders = orderPersistenceAdapter.findByUserId(userId);
            log.info("Found {} orders for user with id: {}", orders.size(), userId);
            return orders.stream()
                    .map(OrderResponseMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error occurred while finding orders for user with id: {}", userId, e);
            throw e;
        }
    }
}