package com.food.ordering.system.springcleanarchitecturecqrs.notification.application.dto.query;

import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.enums.NotificationStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponseDto {

    private String message;
    private Long userId;
    private Long orderId;
    private Long paymentId;
    private NotificationStatus status;
    private Boolean isPaymentApproved;
}