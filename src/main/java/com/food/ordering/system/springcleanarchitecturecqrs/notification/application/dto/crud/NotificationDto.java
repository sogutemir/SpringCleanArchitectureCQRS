package com.food.ordering.system.springcleanarchitecturecqrs.notification.application.dto.crud;

import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.enums.NotificationStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDto {

    @NotBlank(message = "Message cannot be blank")
    private String message;

    private Long productId;
    private Long userId;
    private Long orderId;
    private Long paymentId;
    private NotificationStatus status;
}