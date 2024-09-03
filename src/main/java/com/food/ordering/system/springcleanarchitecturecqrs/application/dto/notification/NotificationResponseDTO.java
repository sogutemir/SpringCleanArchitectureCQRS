package com.food.ordering.system.springcleanarchitecturecqrs.application.dto.notification;

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