package com.food.ordering.system.springcleanarchitecturecqrs.product.domain.event;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductNotificationEvent {
    private Long orderId;
    private Long userId;
    private Long productId;

    private String userName;

    private String message;
    private Integer quantity;
}
