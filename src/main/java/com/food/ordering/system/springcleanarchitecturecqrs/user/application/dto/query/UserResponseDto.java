package com.food.ordering.system.springcleanarchitecturecqrs.user.application.dto.query;

import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.dto.query.NotificationResponseDto;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.dto.query.OrderResponseDto;
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