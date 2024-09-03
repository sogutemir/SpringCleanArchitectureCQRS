package com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.crud;

import com.food.ordering.system.springcleanarchitecturecqrs.order.application.exception.OrderNotFoundException;
import com.food.ordering.system.springcleanarchitecturecqrs.order.dataaccess.adapter.OrderPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.common.application.service.ProductValidationService;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.service.OrderCalculationService;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.dto.OrderDTO;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.mapper.OrderMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class UpdateOrderUseCase {

    private final OrderPersistenceAdapter orderPersistenceAdapter;
    private final ProductValidationService productValidationService;
    private final OrderCalculationService orderCalculationService;

    public Optional<OrderDTO> execute(Long id, OrderDTO orderDTO, Map<Long, Integer> productIdQuantityMap) {
        try {
            log.info("Updating order with id: {}", id);
            Optional<Order> existingOrder = orderPersistenceAdapter.findById(id);

            if (existingOrder.isPresent()) {
                Order order = existingOrder.get();

                List<Product> products = productValidationService.validateProductsExistAndStock(productIdQuantityMap);
                order.setTotalAmount(orderCalculationService.calculateTotalAmount(products, productIdQuantityMap));

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