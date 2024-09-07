package com.food.ordering.system.springcleanarchitecturecqrs.order.application.dto.query;

import com.food.ordering.system.springcleanarchitecturecqrs.product.application.dto.query.ProductResponseDto;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {

    private Long id;
    private Long userId;
    private BigDecimal totalAmount;
    private String orderStatus;
    private List<ProductResponseDto> products;
}