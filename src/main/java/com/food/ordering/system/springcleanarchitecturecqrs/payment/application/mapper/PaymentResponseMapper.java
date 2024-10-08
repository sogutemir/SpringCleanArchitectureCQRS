package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.mapper;


import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.dto.query.PaymentResponseDto;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.entity.Payment;

public class PaymentResponseMapper {

    public static PaymentResponseDto toDTO(Payment payment) {
        if (payment == null) {
            return null;
        }
        return PaymentResponseDto.builder()
                .id(payment.getId())
                .userId(payment.getUser().getId())
                .orderId(payment.getOrder().getId())
                .amount(payment.getAmount())
                .isApproved(payment.getIsApproved())
                .build();
    }
}