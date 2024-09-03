package com.food.ordering.system.springcleanarchitecturecqrs.domain.entity;

import com.food.ordering.system.springcleanarchitecturecqrs.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @DecimalMin(value = "0.0", inclusive = false, message = "Money cannot be less than zero")
    private BigDecimal money;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    private List<Product> products;

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;

    protected void onCreate() {
        super.onCreate();
        this.money = BigDecimal.ZERO;
    }
}