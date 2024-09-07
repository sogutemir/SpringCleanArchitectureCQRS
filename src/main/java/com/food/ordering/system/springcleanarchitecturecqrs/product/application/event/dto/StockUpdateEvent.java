package com.food.ordering.system.springcleanarchitecturecqrs.product.application.event.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockUpdateEvent {
    private Long orderId;
    private Map<Long, Integer> productQuantities;
}
