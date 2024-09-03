package com.food.ordering.system.springcleanarchitecturecqrs.domain.entity;

import com.food.ordering.system.springcleanarchitecturecqrs.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @DecimalMin(value = "0.0", inclusive = false, message = "Amount cannot be less than zero")
    private BigDecimal amount;

    private Boolean isApproved;
}