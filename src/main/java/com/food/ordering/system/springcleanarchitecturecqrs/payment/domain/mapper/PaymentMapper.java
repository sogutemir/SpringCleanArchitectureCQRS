package com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.mapper;

import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.dto.PaymentDto;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.entity.Payment;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;

public class PaymentMapper {

    public static Payment toEntity(PaymentDto paymentDTO) {
        if (paymentDTO == null) {
            return null;
        }
        return Payment.builder()
                .id(paymentDTO.getPaymentId())
                .user(User.builder().id(paymentDTO.getUserId()).build())
                .order(Order.builder().id(paymentDTO.getOrderId()).build())
                .amount(paymentDTO.getAmount())
                .isApproved(true)
                .build();
    }

    public static PaymentDto toDTO(Payment payment) {
        if (payment == null) {
            return null;
        }
        return PaymentDto.builder()
                .paymentId(payment.getId())
                .userId(payment.getUser().getId())
                .orderId(payment.getOrder().getId())
                .amount(payment.getAmount())
                .build();
    }

    public static void partialUpdate(PaymentDto paymentDTO, Payment payment) {
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