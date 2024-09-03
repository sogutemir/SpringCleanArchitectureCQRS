package com.food.ordering.system.springcleanarchitecturecqrs.common.application.service;

import com.food.ordering.system.springcleanarchitecturecqrs.product.application.exception.InsufficientStockException;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.exception.ProductNotFoundException;
import com.food.ordering.system.springcleanarchitecturecqrs.product.dataaccess.adapter.ProductPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ProductValidationService {

    private final ProductPersistenceAdapter productPersistenceAdapter;

    public ProductValidationService(ProductPersistenceAdapter productPersistenceAdapter) {
        this.productPersistenceAdapter = productPersistenceAdapter;
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
}