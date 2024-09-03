package com.food.ordering.system.springcleanarchitecturecqrs.user.domain.mapper;

import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.dto.UserDTO;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;

public class UserMapper {

    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        return User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .money(userDTO.getMoney())
                .build();
    }

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        return UserDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .money(user.getMoney())
                .build();
    }

    public static void partialUpdate(UserDTO userDTO, User user) {
        if (userDTO == null || user == null) {
            return;
        }
        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getMoney() != null) {
            user.setMoney(userDTO.getMoney());
        }
    }
}