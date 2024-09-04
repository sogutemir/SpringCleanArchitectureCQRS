package com.food.ordering.system.springcleanarchitecturecqrs.order.domain.event;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEvent {
    private Long orderId;
    private String status;
    private String message;
    private BigDecimal totalAmount;
    private Long userId;

    public OrderEvent(Long orderId, BigDecimal totalAmount, Long userId) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.userId = userId;
    }
}