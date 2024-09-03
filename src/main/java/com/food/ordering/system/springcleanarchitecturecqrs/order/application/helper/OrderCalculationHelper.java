package com.food.ordering.system.springcleanarchitecturecqrs.order.application.helper;

import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class OrderCalculationHelper {

    public BigDecimal calculateTotalAmount(List<Product> products, Map<Long, Integer> productIdQuantityMap) {
        log.info("Calculating total amount for products: {}", productIdQuantityMap.keySet());
        BigDecimal totalAmount = products.stream()
                .map(product -> product.getPrice().multiply(new BigDecimal(productIdQuantityMap.get(product.getId()))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("Total amount calculated: {}", totalAmount);
        return totalAmount;
    }
}