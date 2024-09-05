package com.food.ordering.system.springcleanarchitecturecqrs.product.application.usecase.command;

import com.food.ordering.system.springcleanarchitecturecqrs.product.dataaccess.adapter.ProductPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Transactional
public class StockUpdateUseCase {

    private final ProductPersistenceAdapter productPersistenceAdapter;

    public StockUpdateUseCase(ProductPersistenceAdapter productPersistenceAdapter) {
        this.productPersistenceAdapter = productPersistenceAdapter;
    }

    public void execute(List<Long> productIds, Map<Long, Integer> productQuantities) {
        log.info("Product stock update use case started for products: {}", productIds);

        List<Product> products = productPersistenceAdapter.findAllByIds(productIds);

        for (Product product : products) {
            Integer quantity = productQuantities.get(product.getId());
            if (quantity != null && quantity > 0) {
                product.adjustStock(quantity);
                productPersistenceAdapter.save(product);
                log.info("Product stock updated for product id: {}. Remaining stock: {}", product.getId(), product.getStockQuantity());
            }
        }

        log.info("Product stock update completed.");
    }
}
