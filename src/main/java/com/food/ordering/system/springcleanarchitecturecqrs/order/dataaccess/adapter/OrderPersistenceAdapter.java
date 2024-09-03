package com.food.ordering.system.springcleanarchitecturecqrs.order.dataaccess.adapter;

import com.food.ordering.system.springcleanarchitecturecqrs.order.dataaccess.repository.crud.OrderCrudRepository;
import com.food.ordering.system.springcleanarchitecturecqrs.order.dataaccess.repository.query.OrderQueryRepository;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderPersistenceAdapter {

    private final OrderCrudRepository orderCrudRepository;
    private final OrderQueryRepository orderQueryRepository;

    public OrderPersistenceAdapter(OrderCrudRepository orderCrudRepository, OrderQueryRepository orderQueryRepository) {
        this.orderCrudRepository = orderCrudRepository;
        this.orderQueryRepository = orderQueryRepository;
    }

    public Optional<Order> findById(Long id) {
        return orderQueryRepository.findById(id);
    }

    public List<Order> findByUserId(Long userId) {
        return orderQueryRepository.findByUserId(userId);
    }

    public List<Order> findByOrderStatus(String status) {
        return orderQueryRepository.findByOrderStatus(status);
    }

    public Order save(Order order) {
        return orderCrudRepository.save(order);
    }

    public void deleteById(Long id) {
        orderCrudRepository.deleteById(id);
    }
}