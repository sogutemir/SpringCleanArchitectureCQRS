package com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.command;

import com.food.ordering.system.springcleanarchitecturecqrs.order.application.exception.OrderNotFoundException;
import com.food.ordering.system.springcleanarchitecturecqrs.order.dataaccess.adapter.OrderPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.usecase.validation.ProductValidationUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.helper.OrderCalculationHelper;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.dto.crud.OrderDto;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.event.dto.OrderUpdateEvent;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.mapper.OrderMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.mapper.OrderUpdateEventToOrderMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class UpdateOrderUseCase {

    private final OrderPersistenceAdapter orderPersistenceAdapter;
    private final ProductValidationUseCase productValidationUseCase;
    private final OrderCalculationHelper orderCalculationHelper;

    public Optional<OrderDto> execute(Long id, OrderDto orderDTO) {
        try {
            log.info("Updating order with id: {}", id);
            Optional<Order> existingOrder = orderPersistenceAdapter.findById(id);

            if (existingOrder.isPresent()) {
                Map<Long, Integer> productQuantities = orderDTO.getProductQuantities();
                List<Product> products = productValidationUseCase.validateProductsExistAndStock(productQuantities);
                BigDecimal totalAmount = orderCalculationHelper.calculateTotalAmount(products, productQuantities);

                Order updatedOrder = OrderMapper.toEntity(orderDTO, products);
                updatedOrder.setTotalAmount(totalAmount);
                updatedOrder.setId(id);
                updatedOrder = orderPersistenceAdapter.save(updatedOrder);

                log.info("Order updated successfully with id: {}", updatedOrder.getId());
                return Optional.of(OrderMapper.toDTO(updatedOrder, productQuantities));
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

    public void execute(Long id, OrderUpdateEvent orderUpdateEvent) {
        try {
            log.info("Updating order status for order id: {}", id);
            Optional<Order> existingOrder = orderPersistenceAdapter.findById(id);

            if (existingOrder.isPresent()) {
                Order order = existingOrder.get();
                OrderUpdateEventToOrderMapper.updateOrderStatus(orderUpdateEvent, order);
                orderPersistenceAdapter.save(order);
                log.info("Order status updated successfully for order id: {}", id);
            } else {
                throw new OrderNotFoundException(id);
            }
        } catch (OrderNotFoundException e) {
            log.error("Order with id {} not found.", id);
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while updating order status for order id: {}", id, e);
            throw e;
        }
    }

}
