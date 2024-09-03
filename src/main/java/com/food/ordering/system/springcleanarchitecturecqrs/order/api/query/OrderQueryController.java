package com.food.ordering.system.springcleanarchitecturecqrs.order.api.query;

import com.food.ordering.system.springcleanarchitecturecqrs.order.application.service.query.OrderQueryService;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.dto.OrderResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders/query")
public class OrderQueryController {

    private final OrderQueryService orderQueryService;

    public OrderQueryController(OrderQueryService orderQueryService) {
        this.orderQueryService = orderQueryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> findOrderById(@PathVariable Long id) {
        Optional<OrderResponseDTO> order = orderQueryService.findOrderById(id);
        return order.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> findOrdersByUserId(@PathVariable Long userId) {
        List<OrderResponseDTO> orders = orderQueryService.findOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }
}