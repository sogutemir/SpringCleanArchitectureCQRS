package com.food.ordering.system.springcleanarchitecturecqrs.order.application.dto.crud;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

    @NotNull(message = "User ID is required")
    private Long userId;

    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount cannot be less than zero")
    private BigDecimal totalAmount;

    @NotNull(message = "Product quantities are required")
    private Map<Long, Integer> productQuantities;
}
