package com.food.ordering.system.springcleanarchitecturecqrs.user.application.mapper;

import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.entity.Order;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;
import com.food.ordering.system.springcleanarchitecturecqrs.user.application.dto.event.UserUpdateEventDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UserUpdateEventToUserMapper {

    public static void updateUser(UserUpdateEventDto userUpdateEventDto, User existingUser) {
        if (userUpdateEventDto == null) {
            return;
        }

        existingUser.setName(userUpdateEventDto.getName());
        existingUser.setEmail(userUpdateEventDto.getEmail());
        existingUser.setMoney(new BigDecimal(userUpdateEventDto.getMoney()));

        List<Order> orders = existingUser.getOrders() != null ? new ArrayList<>(existingUser.getOrders()) : new ArrayList<>();
        orders.add(Order.builder().id(userUpdateEventDto.getOrderId()).build());
        existingUser.setOrders(orders);
    }

    public static UserUpdateEventDto toUserUpdateEvent(User user, Long orderId) {
        if (user == null) {
            return null;
        }

        return UserUpdateEventDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .money(user.getMoney().toString())
                .orderId(orderId)
                .build();
    }
}