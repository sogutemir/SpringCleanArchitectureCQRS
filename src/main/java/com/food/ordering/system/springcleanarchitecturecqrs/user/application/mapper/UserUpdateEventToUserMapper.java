package com.food.ordering.system.springcleanarchitecturecqrs.user.application.mapper;

import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;
import com.food.ordering.system.springcleanarchitecturecqrs.user.application.event.dto.UserUpdateEvent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UserUpdateEventToUserMapper {

    public static void updateUser(UserUpdateEvent userUpdateEvent, User existingUser) {
        if (userUpdateEvent == null) {
            return;
        }

        existingUser.setName(userUpdateEvent.getName());
        existingUser.setEmail(userUpdateEvent.getEmail());
        existingUser.setMoney(new BigDecimal(userUpdateEvent.getMoney()));

        List<Order> orders = existingUser.getOrders() != null ? new ArrayList<>(existingUser.getOrders()) : new ArrayList<>();
        orders.add(Order.builder().id(userUpdateEvent.getOrderId()).build());
        existingUser.setOrders(orders);
    }

    public static UserUpdateEvent toUserUpdateEvent(User user, Long orderId) {
        if (user == null) {
            return null;
        }

        return UserUpdateEvent.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .money(user.getMoney().toString())
                .orderId(orderId)
                .build();
    }
}