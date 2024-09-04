package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.helper;

import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.enums.OrderStatus;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class PaymentHelper {

    public boolean validateFunds(User user, BigDecimal totalAmount) {
        if (user.getMoney().compareTo(totalAmount) < 0) {
            log.error("Insufficient funds for user with id: {}", user.getId());
            return true;
        }
        return false;
    }

    public void updateUserBalance(User user, BigDecimal totalAmount) {
        user.setMoney(user.getMoney().subtract(totalAmount));
    }

    public void updateOrderStatus(Order order, boolean isSuccess) {
        if (isSuccess) {
            order.setOrderStatus(OrderStatus.APPROVED);
            log.info("Order status updated to APPROVED. Order id: {}", order.getId());
        } else {
            order.setOrderStatus(OrderStatus.CANCELLED);
            log.info("Order status updated to CANCELLED. Order id: {}", order.getId());
        }
    }
}
