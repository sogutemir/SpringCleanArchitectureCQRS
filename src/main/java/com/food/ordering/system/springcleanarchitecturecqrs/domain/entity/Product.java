package com.food.ordering.system.springcleanarchitecturecqrs.domain.entity;

import com.food.ordering.system.springcleanarchitecturecqrs.common.entity.BaseEntity;
import com.food.ordering.system.springcleanarchitecturecqrs.domain.enums.ProductStatus;
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
public class Product extends BaseEntity {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price cannot be less than zero")
    private BigDecimal price;

    @Min(value = 0, message = "Stock quantity cannot be less than zero")
    private Integer stockQuantity;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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