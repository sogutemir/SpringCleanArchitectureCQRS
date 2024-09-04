package com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity;

import com.food.ordering.system.springcleanarchitecturecqrs.common.domain.entity.BaseEntity;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.enums.ProductStatus;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "products")
public class Product extends BaseEntity {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price cannot be less than zero")
    private BigDecimal price = BigDecimal.ZERO;

    @Min(value = 0, message = "Stock quantity cannot be less than zero")
    private Integer stockQuantity;

    @ManyToMany(mappedBy = "products")
    private List<Order> orders;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    protected void onCreate() {
        super.onCreate();
        this.productStatus = this.stockQuantity > 0 ? ProductStatus.INSTOCK : ProductStatus.OUTSTOCK;
    }

    public void adjustStock(int quantity) {
        this.stockQuantity -= quantity;
        this.productStatus = this.stockQuantity > 0 ? ProductStatus.INSTOCK : ProductStatus.OUTSTOCK;
    }
}