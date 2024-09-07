package com.food.ordering.system.springcleanarchitecturecqrs.user.application.event.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateEvent {
    private Long userId;
    private String name;
    private String email;
    private String money;
    private Long orderId;
}