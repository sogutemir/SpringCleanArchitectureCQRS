package com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.entity;

import com.food.ordering.system.springcleanarchitecturecqrs.common.entity.BaseEntity;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.entity.Payment;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "notifications")
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