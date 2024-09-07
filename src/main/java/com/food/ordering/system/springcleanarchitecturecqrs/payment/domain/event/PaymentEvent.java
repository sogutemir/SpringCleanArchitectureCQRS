package com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.event;

import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.dto.crud.PaymentDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentEvent {
    private boolean success;
    private String message;
    private PaymentDto paymentDTO;
}
