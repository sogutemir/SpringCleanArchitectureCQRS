package com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.event;

import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.dto.PaymentDTO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentEventDTO {
    private boolean success;
    private String message;
    private PaymentDTO paymentDTO;
}
