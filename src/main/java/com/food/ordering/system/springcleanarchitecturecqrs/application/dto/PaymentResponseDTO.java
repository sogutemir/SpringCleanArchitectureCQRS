package com.food.ordering.system.springcleanarchitecturecqrs.application.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponseDTO {

    private Long id;
    private Long userId;
    private Long orderId;
    private BigDecimal amount;
    private Boolean isApproved;
}