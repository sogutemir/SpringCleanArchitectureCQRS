package com.food.ordering.system.springcleanarchitecturecqrs.application.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {

    @NotNull(message = "User ID is required")
    private Long userId;

    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount cannot be less than zero")
    private BigDecimal totalAmount;

    @NotNull(message = "Products are required")
    private List<Long> productIds;
}