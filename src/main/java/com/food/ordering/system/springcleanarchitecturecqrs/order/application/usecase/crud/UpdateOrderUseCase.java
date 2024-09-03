package com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.crud;

import com.food.ordering.system.springcleanarchitecturecqrs.order.application.exception.OrderNotFoundException;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.helper.OrderValidationHelper;
import com.food.ordering.system.springcleanarchitecturecqrs.order.dataaccess.adapter.OrderPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.dto.OrderDTO;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.mapper.OrderMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class UpdateOrderUseCase {

    private final OrderPersistenceAdapter orderPersistenceAdapter;
    private final OrderValidationHelper orderValidationHelper;

    public UpdateOrderUseCase(OrderPersistenceAdapter orderPersistenceAdapter, OrderValidationHelper orderValidationHelper) {
        this.orderPersistenceAdapter = orderPersistenceAdapter;
        this.orderValidationHelper = orderValidationHelper;
    }

    public Optional<OrderDTO> execute(Long id, OrderDTO orderDTO, Map<Long, Integer> productIdQuantityMap) {
        try {
            log.info("Updating order with id: {}", id);
            Optional<Order> existingOrder = orderPersistenceAdapter.findById(id);

            if (existingOrder.isPresent()) {
                Order order = existingOrder.get();

                List<Product> products = orderValidationHelper.validateProductsExistAndStock(productIdQuantityMap);

                order.setTotalAmount(orderValidationHelper.calculateTotalAmount(products, productIdQuantityMap));

                OrderMapper.partialUpdate(orderDTO, order, products);
                Order updatedOrder = orderPersistenceAdapter.save(order);
                log.info("Order updated successfully with id: {}", updatedOrder.getId());

                return Optional.of(OrderMapper.toDTO(updatedOrder));
            } else {
                throw new OrderNotFoundException(id);
            }
        } catch (OrderNotFoundException e) {
            log.error("Order with id {} not found.", id);
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while updating order with id: {}", id, e);
            throw e;
        }
    }
}