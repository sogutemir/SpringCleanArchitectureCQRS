package com.food.ordering.system.springcleanarchitecturecqrs.user.domain.dto;

import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.dto.NotificationResponseDTO;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.dto.OrderResponseDTO;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.dto.ProductResponseDTO;
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
    private List<NotificationResponseDTO> notifications;
}