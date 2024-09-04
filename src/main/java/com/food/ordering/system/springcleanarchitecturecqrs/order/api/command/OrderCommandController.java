package com.food.ordering.system.springcleanarchitecturecqrs.order.api.command;

import com.food.ordering.system.springcleanarchitecturecqrs.order.application.exception.OrderNotFoundException;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.service.command.OrderCommandService;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.dto.OrderDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders/command")
public class OrderCommandController {

    private final OrderCommandService orderCommandService;

    public OrderCommandController(OrderCommandService orderCommandService) {
        this.orderCommandService = orderCommandService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO, @RequestParam Map<Long, Integer> productIdQuantityMap) {
        OrderDTO createdOrder = orderCommandService.createOrder(orderDTO, productIdQuantityMap);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO, @RequestParam Map<Long, Integer> productIdQuantityMap) {
        Optional<OrderDTO> updatedOrder = orderCommandService.updateOrder(id, orderDTO, productIdQuantityMap);
        return updatedOrder.map(order -> new ResponseEntity<>(order, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderCommandService.deleteOrder(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (OrderNotFoundException e) {
            throw new OrderNotFoundException(id);
        }
    }
}