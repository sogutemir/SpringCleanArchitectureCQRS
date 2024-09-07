package com.food.ordering.system.springcleanarchitecturecqrs.product.application.dto.event;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockUpdateEventDto {
    private Long orderId;
    private Map<Long, Integer> productQuantities;
}
