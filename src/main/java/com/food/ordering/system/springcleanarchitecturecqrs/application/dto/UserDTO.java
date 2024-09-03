package com.food.ordering.system.springcleanarchitecturecqrs.application.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @DecimalMin(value = "0.0", inclusive = false, message = "Money cannot be less than zero")
    private BigDecimal money;
}