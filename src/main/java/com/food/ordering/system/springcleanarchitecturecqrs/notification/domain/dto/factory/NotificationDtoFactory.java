package com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.dto.factory;

import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.dto.crud.NotificationDto;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.enums.NotificationStatus;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.event.PaymentEvent;
import org.springframework.stereotype.Component;

@Component
public class NotificationDtoFactory {

    public static NotificationDto createNotificationDto(PaymentEvent paymentEvent) {
        NotificationDto.NotificationDtoBuilder notificationBuilder = NotificationDto.builder()
                .orderId(paymentEvent.getPaymentDTO().getOrderId())
                .userId(paymentEvent.getPaymentDTO().getUserId());

        if (paymentEvent.getPaymentDTO().getPaymentId() != null) {
            notificationBuilder.paymentId(paymentEvent.getPaymentDTO().getPaymentId())
                    .message("Payment has been created successfully")
                    .status(NotificationStatus.SENT);
        } else {
            notificationBuilder.message("Payment could not be made due to insufficient funds")
                    .status(NotificationStatus.SENT);
        }

        return notificationBuilder.build();
    }
}