package com.food.ordering.system.springcleanarchitecturecqrs.user.application.dto.event;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateEventDto {
    private Long userId;
    private String name;
    private String email;
    private String money;
    private Long orderId;
}