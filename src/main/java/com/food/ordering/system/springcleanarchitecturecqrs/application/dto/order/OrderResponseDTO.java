package com.food.ordering.system.springcleanarchitecturecqrs.application.dto.order;

import com.food.ordering.system.springcleanarchitecturecqrs.application.dto.product.ProductResponseDTO;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO {

    private Long id;
    private Long userId;
    private BigDecimal totalAmount;
    private String orderStatus;
    private List<ProductResponseDTO> products;
}