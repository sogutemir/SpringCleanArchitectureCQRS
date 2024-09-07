package com.food.ordering.system.springcleanarchitecturecqrs.notification.application.dto.factory;

import com.food.ordering.system.springcleanarchitecturecqrs.notification.application.dto.crud.NotificationDto;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.enums.NotificationStatus;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.dto.event.PaymentCreatedEventDto;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.dto.event.ProductNotificationEventDto;
import org.springframework.stereotype.Component;

@Component
public class NotificationDtoFactory {

    public static NotificationDto createNotificationDto(PaymentCreatedEventDto paymentCreatedEventDto) {
        NotificationDto.NotificationDtoBuilder notificationBuilder = NotificationDto.builder()
                .orderId(paymentCreatedEventDto.getPaymentDTO().getOrderId())
                .userId(paymentCreatedEventDto.getPaymentDTO().getUserId());

        if (paymentCreatedEventDto.getPaymentDTO().getPaymentId() != null) {
            notificationBuilder.paymentId(paymentCreatedEventDto.getPaymentDTO().getPaymentId())
                    .message("Payment has been created successfully")
                    .status(NotificationStatus.SENT);
        } else {
            notificationBuilder.message("Payment could not be made due to insufficient funds")
                    .status(NotificationStatus.SENT);
        }

        return notificationBuilder.build();
    }

    public static NotificationDto createProductNotificationDto(ProductNotificationEventDto productNotificationEventDto) {
        String message = productNotificationEventDto.getMessage().isEmpty()
                ? "Product purchased by: " + productNotificationEventDto.getUserName()
                : productNotificationEventDto.getMessage() + ": " + productNotificationEventDto.getProductId();

        return NotificationDto.builder()
                .productId(productNotificationEventDto.getProductId())
                .orderId(productNotificationEventDto.getOrderId())
                .userId(productNotificationEventDto.getUserId())
                .message(message)
                .status(NotificationStatus.SENT)
                .build();
    }
}