package com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.command;

import com.food.ordering.system.springcleanarchitecturecqrs.order.application.exception.OrderNotFoundException;
import com.food.ordering.system.springcleanarchitecturecqrs.order.dataaccess.adapter.OrderPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
public class DeleteOrderUseCaseTest {

    private DeleteOrderUseCase deleteOrderUseCase;
    private OrderPersistenceAdapter orderPersistenceAdapter;

    @BeforeEach
    void setUp() {
        orderPersistenceAdapter = mock(OrderPersistenceAdapter.class);
        deleteOrderUseCase = new DeleteOrderUseCase(orderPersistenceAdapter);
    }

    @Test
    void testDeleteOrder_Success() {

        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        when(orderPersistenceAdapter.findById(orderId)).thenReturn(Optional.of(order));

        deleteOrderUseCase.execute(orderId);

        verify(orderPersistenceAdapter, times(1)).deleteById(orderId);
    }

    @Test
    void testDeleteOrder_OrderNotFound() {

        Long orderId = 1L;
        when(orderPersistenceAdapter.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> deleteOrderUseCase.execute(orderId));
    }
}