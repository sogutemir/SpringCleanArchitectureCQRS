package com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.mapper;

import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.dto.PaymentDto;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.event.PaymentEvent;

public class PaymentDtoToPaymentEventMapper {

    public static PaymentEvent toEvent(PaymentDto paymentDTO) {
        if (paymentDTO == null) {
            return null;
        }
        return PaymentEvent.builder()
                .success(true)
                .message("Payment created")
                .paymentDTO(paymentDTO)
                .build();
    }
}