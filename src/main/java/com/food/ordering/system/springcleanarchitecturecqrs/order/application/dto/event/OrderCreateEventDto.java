package com.food.ordering.system.springcleanarchitecturecqrs.order.application.dto.event;

import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCreateEventDto {
    private Long orderId;
    private String status;
    private String message;
    private BigDecimal totalAmount;
    private Long userId;
    private Map<Long, Integer> productQuantities;

    public OrderCreateEventDto(Long orderId, BigDecimal totalAmount, Long userId, Map<Long, Integer> productQuantities) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.userId = userId;
        this.productQuantities = productQuantities;
    }
}
