package com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.mapper;


import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.dto.PaymentResponseDTO;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.entity.Payment;

public class PaymentResponseMapper {

    public static PaymentResponseDTO toDTO(Payment payment) {
        if (payment == null) {
            return null;
        }
        return PaymentResponseDTO.builder()
                .id(payment.getId())
                .userId(payment.getUser().getId())
                .orderId(payment.getOrder().getId())
                .amount(payment.getAmount())
                .isApproved(payment.getIsApproved())
                .build();
    }
}