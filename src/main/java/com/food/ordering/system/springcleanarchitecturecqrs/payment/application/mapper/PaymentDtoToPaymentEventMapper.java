package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.mapper;

import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.dto.crud.PaymentDto;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.dto.event.PaymentCreatedEventDto;

public class PaymentDtoToPaymentEventMapper {

    public static PaymentCreatedEventDto toEvent(PaymentDto paymentDTO) {
        if (paymentDTO == null) {
            return null;
        }
        return PaymentCreatedEventDto.builder()
                .success(true)
                .message("Payment created")
                .paymentDTO(paymentDTO)
                .build();
    }
}