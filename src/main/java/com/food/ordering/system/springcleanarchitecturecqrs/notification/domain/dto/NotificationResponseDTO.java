package com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponseDTO {

    private Long id;
    private String message;
    private Long userId;
    private Long orderId;
    private Long paymentId;
}