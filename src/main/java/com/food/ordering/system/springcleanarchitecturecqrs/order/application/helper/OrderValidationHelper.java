package com.food.ordering.system.springcleanarchitecturecqrs.order.application.helper;

import com.food.ordering.system.springcleanarchitecturecqrs.product.application.exception.InsufficientStockException;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.exception.ProductNotFoundException;
import com.food.ordering.system.springcleanarchitecturecqrs.product.dataaccess.adapter.ProductPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;
import com.food.ordering.system.springcleanarchitecturecqrs.user.dataaccess.adapter.UserPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class OrderValidationHelper {

    private final UserPersistenceAdapter userPersistenceAdapter;
    private final ProductPersistenceAdapter productPersistenceAdapter;

    public User validateUserExists(Long userId) {
        log.info("Validating user existence for userId: {}", userId);
        Optional<User> user = userPersistenceAdapter.findById(userId);
        if (user.isEmpty()) {
            log.error("User not found with id: {}", userId);
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
        log.info("User with id: {} found", userId);
        return user.get();
    }

    public List<Product> validateProductsExistAndStock(Map<Long, Integer> productIdQuantityMap) {
        log.info("Validating existence and stock for products: {}", productIdQuantityMap.keySet());
        List<Long> productIds = new ArrayList<>(productIdQuantityMap.keySet());
        List<Product> products = productPersistenceAdapter.findAllByIds(productIds);

        if (products.size() != productIds.size()) {
            log.error("One or more products not found for ids: {}", productIds);
            throw new ProductNotFoundException("One or more products not found");
        }

        for (Product product : products) {
            Integer requestedQuantity = productIdQuantityMap.get(product.getId());
            if (product.getStockQuantity() < requestedQuantity) {
                log.error("Insufficient stock for product id: {}. Available: {}, Requested: {}",
                        product.getId(), product.getStockQuantity(), requestedQuantity);
                throw new InsufficientStockException("Insufficient stock for product id: " + product.getId());
            }
        }
        log.info("All products validated successfully.");
        return products;
    }

    public BigDecimal calculateTotalAmount(List<Product> products, Map<Long, Integer> productIdQuantityMap) {
        log.info("Calculating total amount for products: {}", productIdQuantityMap.keySet());
        BigDecimal totalAmount = products.stream()
                .map(product -> product.getPrice().multiply(new BigDecimal(productIdQuantityMap.get(product.getId()))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("Total amount calculated: {}", totalAmount);
        return totalAmount;
    }
}