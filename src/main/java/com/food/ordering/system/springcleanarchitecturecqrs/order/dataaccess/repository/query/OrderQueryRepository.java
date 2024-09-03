package com.food.ordering.system.springcleanarchitecturecqrs.order.dataaccess.repository.query;

import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderQueryRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId")
    List<Order> findByUserId(@Param("userId") Long userId);

    @Query("SELECT o FROM Order o WHERE o.orderStatus = :status")
    List<Order> findByOrderStatus(@Param("status") String status);
}