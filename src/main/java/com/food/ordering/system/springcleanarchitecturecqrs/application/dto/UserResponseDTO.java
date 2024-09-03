package com.food.ordering.system.springcleanarchitecturecqrs.application.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private BigDecimal money;
    private List<OrderResponseDTO> orders;
    private List<ProductResponseDTO> products;
    private List<NotificationResponseDTO> notifications;
}