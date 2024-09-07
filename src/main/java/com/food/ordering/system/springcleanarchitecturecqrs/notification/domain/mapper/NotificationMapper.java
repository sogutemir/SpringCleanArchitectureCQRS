package com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.mapper;

import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.dto.crud.NotificationDto;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.entity.Notification;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.entity.Payment;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;

public class NotificationMapper {

    public static Notification toEntity(NotificationDto notificationDTO) {
        if (notificationDTO == null) {
            return null;
        }
        return Notification.builder()
                .message(notificationDTO.getMessage())
                .user(User.builder().id(notificationDTO.getUserId()).build())
                .product(notificationDTO.getProductId() != null ? Product.builder().id(notificationDTO.getProductId()).build() : null)
                .order(notificationDTO.getOrderId() != null ? Order.builder().id(notificationDTO.getOrderId()).build() : null)
                .payment(notificationDTO.getPaymentId() != null ? Payment.builder().id(notificationDTO.getPaymentId()).build() : null)
                .status(notificationDTO.getStatus())
                .build();
    }

    public static NotificationDto toDto(Notification notification) {
        if (notification == null) {
            return null;
        }
        return NotificationDto.builder()
                .message(notification.getMessage())
                .userId(notification.getUser().getId())
                .orderId(notification.getOrder() != null ? notification.getOrder().getId() : null)
                .paymentId(notification.getPayment() != null ? notification.getPayment().getId() : null)
                .status(notification.getStatus())
                .build();
    }

    public static void partialUpdate(NotificationDto notificationDTO, Notification notification) {
        if (notificationDTO == null || notification == null) {
            return;
        }
        if (notificationDTO.getMessage() != null) {
            notification.setMessage(notificationDTO.getMessage());
        }
        if (notificationDTO.getUserId() != null) {
            notification.setUser(User.builder().id(notificationDTO.getUserId()).build());
        }
        if (notificationDTO.getOrderId() != null) {
            notification.setOrder(Order.builder().id(notificationDTO.getOrderId()).build());
        }
        if (notificationDTO.getPaymentId() != null) {
            notification.setPayment(Payment.builder().id(notificationDTO.getPaymentId()).build());
        }
        if (notificationDTO.getStatus() != null) {
            notification.setStatus(notificationDTO.getStatus());
        }
    }
}