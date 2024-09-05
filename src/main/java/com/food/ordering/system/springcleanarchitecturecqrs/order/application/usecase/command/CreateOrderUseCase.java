package com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.command;

import com.food.ordering.system.springcleanarchitecturecqrs.order.application.event.producer.OrderEventProducer;
import com.food.ordering.system.springcleanarchitecturecqrs.order.dataaccess.adapter.OrderPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.dto.OrderDto;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.event.OrderEvent;
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
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class CreateOrderUseCase {

    private final OrderPersistenceAdapter orderPersistenceAdapter;
    private final UserValidationService userValidationService;
    private final ProductValidationService productValidationService;
    private final OrderCalculationHelper orderCalculationHelper;
    private final OrderEventProducer orderEventProducer;

    public CreateOrderUseCase(OrderPersistenceAdapter orderPersistenceAdapter, UserValidationService userValidationService, ProductValidationService productValidationService, OrderCalculationHelper orderCalculationHelper, OrderEventProducer orderEventProducer) {
        this.orderPersistenceAdapter = orderPersistenceAdapter;
        this.userValidationService = userValidationService;
        this.productValidationService = productValidationService;
        this.orderCalculationHelper = orderCalculationHelper;
        this.orderEventProducer = orderEventProducer;
    }

    public OrderDto execute(OrderDto orderDTO, Map<Long, Integer> productIdQuantityMap) {
        try {
            log.info("Creating order for user with id: {}", orderDTO.getUserId());

            User user = userValidationService.validateUserExists(orderDTO.getUserId());

            List<Product> products = productValidationService.validateProductsExistAndStock(productIdQuantityMap);
            BigDecimal totalAmount = orderCalculationHelper.calculateTotalAmount(products, productIdQuantityMap);

            Order order = OrderMapper.toEntity(orderDTO, products);
            order.setTotalAmount(totalAmount);

            Order savedOrder = orderPersistenceAdapter.save(order);

            log.info("Order created successfully with id: {}", savedOrder.getId());

            try {
                OrderEvent orderEvent = new OrderEvent(savedOrder.getId(), savedOrder.getTotalAmount(), savedOrder.getUser().getId());
                orderEventProducer.sendOrderEvent(orderEvent);
                log.info("Order event sent successfully for order id: {}", savedOrder.getId());
            } catch (Exception kafkaException) {
                log.error("Error occurred while sending order event for order id: {}. Error: {}", savedOrder.getId(), kafkaException.getMessage(), kafkaException);
            }

            return OrderMapper.toDTO(savedOrder, productIdQuantityMap);

        } catch (Exception e) {
            log.error("Error occurred while creating order: {}", e.getMessage(), e);
            throw e;
        }
    }


}