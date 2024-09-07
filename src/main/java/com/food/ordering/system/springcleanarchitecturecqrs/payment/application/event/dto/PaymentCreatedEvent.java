package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.event.dto;

import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.dto.crud.PaymentDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentCreatedEvent {
    private boolean success;
    private String message;
    private PaymentDto paymentDTO;
}
