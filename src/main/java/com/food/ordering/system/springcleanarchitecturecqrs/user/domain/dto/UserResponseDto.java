package com.food.ordering.system.springcleanarchitecturecqrs.user.domain.dto;

import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.dto.NotificationResponseDto;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.dto.OrderResponseDto;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

    private Long id;
    private String name;
    private String email;
    private BigDecimal money;
    private List<OrderResponseDto> orders;
    private List<NotificationResponseDto> notifications;
}