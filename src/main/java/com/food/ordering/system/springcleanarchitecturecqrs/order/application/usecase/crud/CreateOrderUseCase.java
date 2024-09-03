package com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.crud;

import com.food.ordering.system.springcleanarchitecturecqrs.order.application.helper.OrderValidationHelper;
import com.food.ordering.system.springcleanarchitecturecqrs.order.dataaccess.adapter.OrderPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.dto.OrderDTO;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.mapper.OrderMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.exception.InsufficientStockException;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.exception.ProductNotFoundException;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class CreateOrderUseCase {

    private final OrderPersistenceAdapter orderPersistenceAdapter;
    private final OrderValidationHelper orderValidationHelper;

    public CreateOrderUseCase(OrderPersistenceAdapter orderPersistenceAdapter, OrderValidationHelper orderValidationHelper) {
        this.orderPersistenceAdapter = orderPersistenceAdapter;
        this.orderValidationHelper = orderValidationHelper;
    }

    public OrderDTO execute(OrderDTO orderDTO, Map<Long, Integer> productIdQuantityMap) {
        try {
            log.info("Creating order for user with id: {}", orderDTO.getUserId());

            User user = orderValidationHelper.validateUserExists(orderDTO.getUserId());

            List<Product> products = orderValidationHelper.validateProductsExistAndStock(productIdQuantityMap);

            BigDecimal totalAmount = orderValidationHelper.calculateTotalAmount(products, productIdQuantityMap);

            Order order = OrderMapper.toEntity(orderDTO, products);
            order.setTotalAmount(totalAmount);

            Order savedOrder = orderPersistenceAdapter.save(order);

            log.info("Order created successfully with id: {}", savedOrder.getId());

            return OrderMapper.toDTO(savedOrder);
        } catch (ProductNotFoundException | InsufficientStockException e) {
            log.error("Error occurred while creating order: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while creating order: {}", e);
            throw new RuntimeException("Unexpected error occurred while creating order", e);
        }
    }
}