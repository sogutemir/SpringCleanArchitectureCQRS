package com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.validation;

import com.food.ordering.system.springcleanarchitecturecqrs.order.application.exception.OrderNotFoundException;
import com.food.ordering.system.springcleanarchitecturecqrs.order.dataaccess.adapter.OrderPersistenceAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderValidationUseCase {

    private final OrderPersistenceAdapter orderPersistenceAdapter;

    public OrderValidationUseCase(OrderPersistenceAdapter orderPersistenceAdapter) {
        this.orderPersistenceAdapter = orderPersistenceAdapter;
    }

    public void validateOrderExists(Long orderId) {
        orderPersistenceAdapter.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}