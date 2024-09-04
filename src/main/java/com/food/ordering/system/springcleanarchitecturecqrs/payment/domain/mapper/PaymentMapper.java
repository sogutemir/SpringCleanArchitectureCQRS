package com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.mapper;

import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.dto.PaymentDTO;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.entity.Payment;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;

public class PaymentMapper {

    public static Payment toEntity(PaymentDTO paymentDTO) {
        if (paymentDTO == null) {
            return null;
        }
        return Payment.builder()
                .user(User.builder().id(paymentDTO.getUserId()).build())
                .order(Order.builder().id(paymentDTO.getOrderId()).build())
                .amount(paymentDTO.getAmount())
                .isApproved(true)
                .build();
    }

    public static PaymentDTO toDTO(Payment payment) {
        if (payment == null) {
            return null;
        }
        return PaymentDTO.builder()
                .userId(payment.getUser().getId())
                .orderId(payment.getOrder().getId())
                .amount(payment.getAmount())
                .build();
    }

    public static void partialUpdate(PaymentDTO paymentDTO, Payment payment) {
        if (paymentDTO == null || payment == null) {
            return;
        }
        if (paymentDTO.getUserId() != null) {
            payment.setUser(User.builder().id(paymentDTO.getUserId()).build());
        }
        if (paymentDTO.getOrderId() != null) {
            payment.setOrder(Order.builder().id(paymentDTO.getOrderId()).build());
        }
        if (paymentDTO.getAmount() != null) {
            payment.setAmount(paymentDTO.getAmount());
        }
    }
}