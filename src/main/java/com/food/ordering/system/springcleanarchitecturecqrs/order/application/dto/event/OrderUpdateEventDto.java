package com.food.ordering.system.springcleanarchitecturecqrs.order.application.dto.event;

import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.enums.OrderStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderUpdateEventDto {
    private Long orderId;
    private OrderStatus status;
}
