package com.food.ordering.system.springcleanarchitecturecqrs.order.application.service.command.impl;

import com.food.ordering.system.springcleanarchitecturecqrs.order.application.service.command.OrderCommandService;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.command.CreateOrderUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.command.DeleteOrderUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.command.UpdateOrderUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.dto.crud.OrderDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public OrderDto createOrder(OrderDto orderDTO) {
        return createOrderUseCase.execute(orderDTO, orderDTO.getProductQuantities());
    }


    @Override
    public Optional<OrderDto> updateOrder(Long id, OrderDto orderDTO) {
        return updateOrderUseCase.execute(id, orderDTO);
    }


    @Override
    public void deleteOrder(Long id) {
        deleteOrderUseCase.execute(id);
    }
}