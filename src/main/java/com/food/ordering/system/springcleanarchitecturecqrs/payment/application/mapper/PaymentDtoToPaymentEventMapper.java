package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.mapper;

import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.dto.crud.PaymentDto;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.event.model.PaymentCreatedEvent;

public class PaymentDtoToPaymentEventMapper {

    public static PaymentCreatedEvent toEvent(PaymentDto paymentDTO) {
        if (paymentDTO == null) {
            return null;
        }
        return PaymentCreatedEvent.builder()
                .success(true)
                .message("Payment created")
                .paymentDTO(paymentDTO)
                .build();
    }
}