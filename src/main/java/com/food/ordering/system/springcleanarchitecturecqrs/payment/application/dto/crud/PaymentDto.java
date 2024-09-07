package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.dto.crud;

import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

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

    private Map<Long, Integer> productQuantities;
}