package com.food.ordering.system.springcleanarchitecturecqrs.product.domain.event;

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
