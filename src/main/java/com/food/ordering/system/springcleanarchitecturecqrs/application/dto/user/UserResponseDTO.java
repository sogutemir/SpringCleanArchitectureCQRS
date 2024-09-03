package com.food.ordering.system.springcleanarchitecturecqrs.application.dto.user;

import com.food.ordering.system.springcleanarchitecturecqrs.application.dto.notification.NotificationResponseDTO;
import com.food.ordering.system.springcleanarchitecturecqrs.application.dto.order.OrderResponseDTO;
import com.food.ordering.system.springcleanarchitecturecqrs.application.dto.product.ProductResponseDTO;
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