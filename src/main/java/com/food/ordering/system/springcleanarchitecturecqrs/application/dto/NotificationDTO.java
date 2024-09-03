package com.food.ordering.system.springcleanarchitecturecqrs.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDTO {

    @NotBlank(message = "Message cannot be blank")
    private String message;

    private Long userId;
    private Long orderId;
    private Long paymentId;
}