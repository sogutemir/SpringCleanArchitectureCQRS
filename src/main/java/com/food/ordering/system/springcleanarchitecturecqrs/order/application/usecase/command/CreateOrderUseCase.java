package com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.command;

import com.food.ordering.system.springcleanarchitecturecqrs.order.dataaccess.adapter.OrderPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.dto.OrderDTO;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.mapper.OrderMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.helper.OrderCalculationHelper;
import com.food.ordering.system.springcleanarchitecturecqrs.common.application.service.ProductValidationService;
import com.food.ordering.system.springcleanarchitecturecqrs.common.application.service.UserValidationService;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Slf4j
@RequiredArgsConstructor
@Component
public class CreateOrderUseCase {

    private final OrderPersistenceAdapter orderPersistenceAdapter;
    private final UserValidationService userValidationService;
    private final ProductValidationService productValidationService;
    private final OrderCalculationHelper orderCalculationHelper;

    public OrderDTO execute(OrderDTO orderDTO, Map<Long, Integer> productIdQuantityMap) {
        try {
            log.info("Creating order for user with id: {}", orderDTO.getUserId());

            User user = userValidationService.validateUserExists(orderDTO.getUserId());
            List<Product> products = productValidationService.validateProductsExistAndStock(productIdQuantityMap);
            BigDecimal totalAmount = orderCalculationHelper.calculateTotalAmount(products, productIdQuantityMap);

            Order order = OrderMapper.toEntity(orderDTO, products);
            order.setTotalAmount(totalAmount);

            Order savedOrder = orderPersistenceAdapter.save(order);

            log.info("Order created successfully with id: {}", savedOrder.getId());

            return OrderMapper.toDTO(savedOrder);
        } catch (Exception e) {
            log.error("Error occurred while creating order: {}", e.getMessage(), e);
            throw e;
        }
    }
}