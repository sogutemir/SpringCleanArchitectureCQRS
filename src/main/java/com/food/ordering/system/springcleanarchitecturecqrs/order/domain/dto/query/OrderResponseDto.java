package com.food.ordering.system.springcleanarchitecturecqrs.order.domain.dto.query;

import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.dto.query.ProductResponseDto;
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