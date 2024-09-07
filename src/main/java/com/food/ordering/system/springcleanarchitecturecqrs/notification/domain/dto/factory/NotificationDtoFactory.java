package com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.dto.factory;

import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.dto.crud.NotificationDto;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.enums.NotificationStatus;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.event.model.PaymentCreatedEvent;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.event.dto.ProductNotificationEvent;
import org.springframework.stereotype.Component;

@Component
public class NotificationDtoFactory {

    public static NotificationDto createNotificationDto(PaymentCreatedEvent paymentCreatedEvent) {
        NotificationDto.NotificationDtoBuilder notificationBuilder = NotificationDto.builder()
                .orderId(paymentCreatedEvent.getPaymentDTO().getOrderId())
                .userId(paymentCreatedEvent.getPaymentDTO().getUserId());

        if (paymentCreatedEvent.getPaymentDTO().getPaymentId() != null) {
            notificationBuilder.paymentId(paymentCreatedEvent.getPaymentDTO().getPaymentId())
                    .message("Payment has been created successfully")
                    .status(NotificationStatus.SENT);
        } else {
            notificationBuilder.message("Payment could not be made due to insufficient funds")
                    .status(NotificationStatus.SENT);
        }

        return notificationBuilder.build();
    }

    public static NotificationDto createProductNotificationDto(ProductNotificationEvent productNotificationEvent) {
        String message = productNotificationEvent.getMessage().isEmpty()
                ? "Product purchased by: " + productNotificationEvent.getUserName()
                : productNotificationEvent.getMessage() + ": " + productNotificationEvent.getProductId();

        return NotificationDto.builder()
                .productId(productNotificationEvent.getProductId())
                .orderId(productNotificationEvent.getOrderId())
                .userId(productNotificationEvent.getUserId())
                .message(message)
                .status(NotificationStatus.SENT)
                .build();
    }
}