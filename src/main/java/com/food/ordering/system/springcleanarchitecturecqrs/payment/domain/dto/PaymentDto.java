package com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDto {

    private Long paymentId;
    private Long userId;
    private Long orderId;

    @DecimalMin(value = "0.0", inclusive = false, message = "Amount cannot be less than zero")
    private BigDecimal amount;
}