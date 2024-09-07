package com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.dto.query;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponseDto {

    private Long id;
    private Long userId;
    private Long orderId;
    private BigDecimal amount;
    private Boolean isApproved;
}