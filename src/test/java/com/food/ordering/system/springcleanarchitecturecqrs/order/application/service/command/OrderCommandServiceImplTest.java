package com.food.ordering.system.springcleanarchitecturecqrs.order.application.service.command;

import com.food.ordering.system.springcleanarchitecturecqrs.order.application.service.command.impl.OrderCommandServiceImpl;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.command.CreateOrderUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.command.DeleteOrderUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.command.UpdateOrderUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.dto.OrderDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class OrderCommandServiceImplTest {

    @Mock
    private CreateOrderUseCase createOrderUseCase;

    @Mock
    private UpdateOrderUseCase updateOrderUseCase;

    @Mock
    private DeleteOrderUseCase deleteOrderUseCase;

    @InjectMocks
    private OrderCommandServiceImpl orderCommandService;

    private OrderDto testOrderDto;
    private Map<Long, Integer> productIdQuantityMap;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testOrderDto = OrderDto.builder().userId(1L).build();
        productIdQuantityMap = new HashMap<>();
        productIdQuantityMap.put(1L, 2);
    }
//
//    @Test
//    void createOrder_Success() {
//
//        when(createOrderUseCase.execute(testOrderDTO, productIdQuantityMap)).thenReturn(testOrderDTO);
//
//        OrderDTO result = orderCommandService.createOrder(testOrderDTO, productIdQuantityMap);
//
//        assertNotNull(result);
//        assertEquals(testOrderDTO, result);
//        verify(createOrderUseCase, times(1)).execute(testOrderDTO, productIdQuantityMap);
//    }
//
//    @Test
//    void updateOrder_Success() {
//
//        Long orderId = 1L;
//        when(updateOrderUseCase.execute(orderId, testOrderDTO, productIdQuantityMap)).thenReturn(Optional.of(testOrderDTO));
//
//        Optional<OrderDTO> result = orderCommandService.updateOrder(orderId, testOrderDTO, productIdQuantityMap);
//
//        assertTrue(result.isPresent());
//        assertEquals(testOrderDTO, result.get());
//        verify(updateOrderUseCase, times(1)).execute(orderId, testOrderDTO, productIdQuantityMap);
//    }

    @Test
    void deleteOrder_Success() {

        Long orderId = 1L;
        doNothing().when(deleteOrderUseCase).execute(orderId);

        orderCommandService.deleteOrder(orderId);

        verify(deleteOrderUseCase, times(1)).execute(orderId);
    }
}