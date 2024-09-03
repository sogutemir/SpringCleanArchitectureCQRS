package com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.mapper;


import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.dto.NotificationDTO;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.entity.Notification;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.entity.Payment;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;

public class NotificationMapper {

    public static Notification toEntity(NotificationDTO notificationDTO) {
        if (notificationDTO == null) {
            return null;
        }
        return Notification.builder()
                .message(notificationDTO.getMessage())
                .user(User.builder().id(notificationDTO.getUserId()).build())
                .order(notificationDTO.getOrderId() != null ? Order.builder().id(notificationDTO.getOrderId()).build() : null)
                .payment(notificationDTO.getPaymentId() != null ? Payment.builder().id(notificationDTO.getPaymentId()).build() : null)
                .build();
    }

    public static void partialUpdate(NotificationDTO notificationDTO, Notification notification) {
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
    }
}