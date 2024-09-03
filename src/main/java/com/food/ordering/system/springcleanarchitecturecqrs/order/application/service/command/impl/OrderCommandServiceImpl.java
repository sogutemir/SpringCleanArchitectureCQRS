package com.food.ordering.system.springcleanarchitecturecqrs.order.application.service.command.impl;

import com.food.ordering.system.springcleanarchitecturecqrs.order.application.service.command.OrderCommandService;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.crud.CreateOrderUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.crud.DeleteOrderUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.crud.UpdateOrderUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class OrderCommandServiceImpl implements OrderCommandService {

    private final CreateOrderUseCase createOrderUseCase;
    private final UpdateOrderUseCase updateOrderUseCase;
    private final DeleteOrderUseCase deleteOrderUseCase;

    public OrderCommandServiceImpl(
            CreateOrderUseCase createOrderUseCase,
            UpdateOrderUseCase updateOrderUseCase,
            DeleteOrderUseCase deleteOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.updateOrderUseCase = updateOrderUseCase;
        this.deleteOrderUseCase = deleteOrderUseCase;
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO, Map<Long, Integer> productIdQuantityMap) {
        log.info("Creating a new order for userId: {}", orderDTO.getUserId());
        return createOrderUseCase.execute(orderDTO, productIdQuantityMap);
    }

    @Override
    public Optional<OrderDTO> updateOrder(Long id, OrderDTO orderDTO, Map<Long, Integer> productIdQuantityMap) {
        log.info("Updating order with id: {}", id);
        return updateOrderUseCase.execute(id, orderDTO, productIdQuantityMap);
    }

    @Override
    public void deleteOrder(Long id) {
        log.info("Deleting order with id: {}", id);
        deleteOrderUseCase.execute(id);
    }
}