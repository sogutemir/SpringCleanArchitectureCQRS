package com.food.ordering.system.springcleanarchitecturecqrs.order.dataaccess.repository.crud;

import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderCrudRepository extends CrudRepository<Order, Long> {

}