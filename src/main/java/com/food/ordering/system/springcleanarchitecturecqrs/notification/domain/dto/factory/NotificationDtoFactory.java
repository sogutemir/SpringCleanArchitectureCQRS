package com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.dto.factory;

import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.dto.crud.NotificationDto;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.enums.NotificationStatus;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.event.PaymentEvent;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.event.ProductNotificationEvent;
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