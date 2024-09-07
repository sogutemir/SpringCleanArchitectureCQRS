package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.dto.event;

import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.dto.crud.PaymentDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentCreatedEventDto {
    private boolean success;
    private String message;
    private PaymentDto paymentDTO;
}
