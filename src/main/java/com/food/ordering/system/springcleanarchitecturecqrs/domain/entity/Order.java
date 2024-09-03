package com.food.ordering.system.springcleanarchitecturecqrs.domain.entity;

import com.food.ordering.system.springcleanarchitecturecqrs.common.entity.BaseEntity;
import com.food.ordering.system.springcleanarchitecturecqrs.domain.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order")
    private List<Payment> payments;

    @ManyToMany
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount cannot be less than zero")
    private BigDecimal totalAmount;

    @NotNull(message = "Order status cannot be null")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    protected void onCreate() {
        super.onCreate();
        this.orderStatus = OrderStatus.PENDING;
    }
}