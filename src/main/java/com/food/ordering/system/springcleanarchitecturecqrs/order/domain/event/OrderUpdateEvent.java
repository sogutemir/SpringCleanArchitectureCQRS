package com.food.ordering.system.springcleanarchitecturecqrs.order.domain.event;

import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.enums.OrderStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderUpdateEvent {
    private Long orderId;
    private OrderStatus status;
}
