package com.food.ordering.system.springcleanarchitecturecqrs.order.api.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.exception.OrderNotFoundException;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.service.command.OrderCommandService;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.dto.crud.OrderDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderCommandController.class)
public class OrderCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderCommandService orderCommandService;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderDto testOrderDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testOrderDto = OrderDto.builder()
                .userId(1L)
                .totalAmount(BigDecimal.valueOf(200))
                .productQuantities(Map.of(1L, 2, 2L, 1))
                .build();
    }

    @Test
    void createOrder_Success() throws Exception {
        when(orderCommandService.createOrder(any(OrderDto.class))).thenReturn(testOrderDto);

        mockMvc.perform(post("/api/v1/orders/command")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testOrderDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.totalAmount").value(200))
                .andExpect(jsonPath("$.productQuantities[1]").value(2))
                .andExpect(jsonPath("$.productQuantities[2]").value(1));

        verify(orderCommandService, times(1)).createOrder(any(OrderDto.class));
    }

    @Test
    void createOrder_BadRequest() throws Exception {
        OrderDto invalidOrderDto = new OrderDto();

        mockMvc.perform(post("/api/v1/orders/command")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidOrderDto)))
                .andExpect(status().isBadRequest());

        verify(orderCommandService, never()).createOrder(any(OrderDto.class));
    }

    @Test
    void updateOrder_Success() throws Exception {
        when(orderCommandService.updateOrder(eq(1L), any(OrderDto.class)))
                .thenReturn(Optional.of(testOrderDto));

        mockMvc.perform(put("/api/v1/orders/command/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testOrderDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.totalAmount").value(200))
                .andExpect(jsonPath("$.productQuantities[1]").value(2))
                .andExpect(jsonPath("$.productQuantities[2]").value(1));

        verify(orderCommandService, times(1)).updateOrder(eq(1L), any(OrderDto.class));
    }

    @Test
    void updateOrder_NotFound() throws Exception {
        when(orderCommandService.updateOrder(eq(999L), any(OrderDto.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/orders/command/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testOrderDto)))
                .andExpect(status().isNotFound());

        verify(orderCommandService, times(1)).updateOrder(eq(999L), any(OrderDto.class));
    }

    @Test
    void deleteOrder_Success() throws Exception {
        doNothing().when(orderCommandService).deleteOrder(1L);

        mockMvc.perform(delete("/api/v1/orders/command/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(orderCommandService, times(1)).deleteOrder(1L);
    }

    @Test
    void deleteOrder_NotFound() throws Exception {
        doThrow(new OrderNotFoundException(999L)).when(orderCommandService).deleteOrder(999L);

        mockMvc.perform(delete("/api/v1/orders/command/{id}", 999L))
                .andExpect(status().isNotFound());

        verify(orderCommandService, times(1)).deleteOrder(999L);
    }

    @Test
    void createOrder_TotalAmountCalculation() throws Exception {
        Long productId1 = 1L;
        Long productId2 = 2L;
        BigDecimal product1Price = BigDecimal.valueOf(100);
        BigDecimal product2Price = BigDecimal.valueOf(50);
        int quantity1 = 2;
        int quantity2 = 3;

        BigDecimal expectedTotalAmount = product1Price.multiply(BigDecimal.valueOf(quantity1))
                .add(product2Price.multiply(BigDecimal.valueOf(quantity2)));

        testOrderDto.setProductQuantities(Map.of(productId1, quantity1, productId2, quantity2));
        testOrderDto.setTotalAmount(expectedTotalAmount);

        when(orderCommandService.createOrder(any(OrderDto.class))).thenReturn(testOrderDto);

        mockMvc.perform(post("/api/v1/orders/command")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testOrderDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.totalAmount").value(350))
                .andExpect(jsonPath("$.productQuantities[1]").value(quantity1))
                .andExpect(jsonPath("$.productQuantities[2]").value(quantity2));

        verify(orderCommandService, times(1)).createOrder(any(OrderDto.class));
    }
}