package com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.query;

import com.food.ordering.system.springcleanarchitecturecqrs.order.application.exception.OrderNotFoundException;
import com.food.ordering.system.springcleanarchitecturecqrs.order.dataaccess.adapter.OrderPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.dto.OrderResponseDTO;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.mapper.OrderResponseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class FindOrderByIdUseCase {

    private final OrderPersistenceAdapter orderPersistenceAdapter;

    public FindOrderByIdUseCase(OrderPersistenceAdapter orderPersistenceAdapter) {
        this.orderPersistenceAdapter = orderPersistenceAdapter;
    }

    public Optional<OrderResponseDTO> execute(Long id) {
        try {
            log.info("Finding order with id: {}", id);
            Optional<Order> order = orderPersistenceAdapter.findById(id);
            if (order.isPresent()) {
                log.info("Order found with id: {}", id);
                return Optional.of(OrderResponseMapper.toDTO(order.get()));
            } else {
                throw new OrderNotFoundException(id);
            }
        } catch (OrderNotFoundException e) {
            log.error("Order with id {} not found.", id);
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while finding order with id: {}", id, e);
            throw e;
        }
    }
}