package com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.command;

import com.food.ordering.system.springcleanarchitecturecqrs.order.application.exception.OrderNotFoundException;
import com.food.ordering.system.springcleanarchitecturecqrs.order.dataaccess.adapter.OrderPersistenceAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
public class DeleteOrderUseCase {

    private final OrderPersistenceAdapter orderPersistenceAdapter;

    public DeleteOrderUseCase(OrderPersistenceAdapter orderPersistenceAdapter) {
        this.orderPersistenceAdapter = orderPersistenceAdapter;
    }

    public void execute(Long id) {
        try {
            log.info("Deleting order with id: {}", id);
            if (orderPersistenceAdapter.findById(id).isPresent()) {
                orderPersistenceAdapter.deleteById(id);
                log.info("Order deleted successfully with id: {}", id);
            } else {
                throw new OrderNotFoundException(id);
            }
        } catch (OrderNotFoundException e) {
            log.error("Order with id {} not found.", id);
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while deleting order with id: {}", id, e);
            throw e;
        }
    }
}