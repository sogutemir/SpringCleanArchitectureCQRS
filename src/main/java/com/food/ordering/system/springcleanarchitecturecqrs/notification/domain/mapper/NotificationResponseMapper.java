package com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.mapper;

import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.dto.NotificationResponseDto;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.entity.Notification;

public class NotificationResponseMapper {

    public static NotificationResponseDto toDto(Notification notification) {
        if (notification == null) {
            return null;
        }
        return NotificationResponseDto.builder()
                .message(notification.getMessage())
                .userId(notification.getUser().getId())
                .orderId(notification.getOrder() != null ? notification.getOrder().getId() : null)
                .paymentId(notification.getPayment() != null ? notification.getPayment().getId() : null)
                .status(notification.getStatus())
                .isPaymentApproved(notification.getPayment() != null)
                .build();
    }
}