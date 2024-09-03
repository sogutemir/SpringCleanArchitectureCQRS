package com.food.ordering.system.springcleanarchitecturecqrs.domain.entity;

import com.food.ordering.system.springcleanarchitecturecqrs.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends BaseEntity {

    @NotBlank(message = "Message cannot be blank")
    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
}