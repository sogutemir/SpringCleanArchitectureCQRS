package com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.command;

import com.food.ordering.system.springcleanarchitecturecqrs.order.application.dto.factory.OrderEventDtoFactory;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.message.SendOrderEventUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.order.dataaccess.adapter.OrderPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.dto.crud.OrderDto;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.dto.event.OrderCreateEventDto;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.mapper.OrderMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.helper.OrderCalculationHelper;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.usecase.validation.ProductValidationUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.user.application.usecase.validation.UserValidationUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Component
@Transactional
public class CreateOrderUseCase {

    private final OrderPersistenceAdapter orderPersistenceAdapter;
    private final UserValidationUseCase userValidationUseCase;
    private final ProductValidationUseCase productValidationUseCase;
    private final OrderCalculationHelper orderCalculationHelper;
    private final SendOrderEventUseCase sendOrderEventUseCase;

    public CreateOrderUseCase(
            OrderPersistenceAdapter orderPersistenceAdapter,
            UserValidationUseCase userValidationUseCase,
            ProductValidationUseCase productValidationUseCase,
            OrderCalculationHelper orderCalculationHelper,
            SendOrderEventUseCase sendOrderEventUseCase) {
        this.orderPersistenceAdapter = orderPersistenceAdapter;
        this.userValidationUseCase = userValidationUseCase;
        this.productValidationUseCase = productValidationUseCase;
        this.orderCalculationHelper = orderCalculationHelper;
        this.sendOrderEventUseCase = sendOrderEventUseCase;
    }

    public OrderDto execute(OrderDto orderDTO, Map<Long, Integer> productIdQuantityMap) {
        try {
            log.info("Creating order for user with id: {}", orderDTO.getUserId());

            User user = userValidationUseCase.validateUserExists(orderDTO.getUserId());
            List<Product> products = productValidationUseCase.validateProductsExistAndStock(productIdQuantityMap);
            BigDecimal totalAmount = orderCalculationHelper.calculateTotalAmount(products, productIdQuantityMap);

            Order order = OrderMapper.toEntity(orderDTO, products);
            order.setTotalAmount(totalAmount);

            Order savedOrder = orderPersistenceAdapter.save(order);
            log.info("Order created successfully with id: {}", savedOrder.getId());

            OrderCreateEventDto orderCreateEventDto = OrderEventDtoFactory.createOrderEvent(savedOrder, productIdQuantityMap);
            sendOrderEventUseCase.execute(orderCreateEventDto);

            return OrderMapper.toDTO(savedOrder, productIdQuantityMap);

        } catch (Exception e) {
            log.error("Error occurred while creating order: {}", e.getMessage(), e);
            throw e;
        }
    }
}
