package com.food.ordering.system.springcleanarchitecturecqrs.application.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price cannot be less than zero")
    private BigDecimal price;

    @Min(value = 0, message = "Stock quantity cannot be less than zero")
    private Integer stockQuantity;
}