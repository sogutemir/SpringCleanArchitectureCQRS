package com.food.ordering.system.springcleanarchitecturecqrs.user.domain.mapper;

import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.dto.UserDto;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;

public class UserMapper {

    public static User toEntity(UserDto userDTO) {
        if (userDTO == null) {
            return null;
        }
        return User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .money(userDTO.getMoney())
                .build();
    }

    public static UserDto toDTO(User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .money(user.getMoney())
                .build();
    }

    public static void partialUpdate(UserDto userDTO, User user) {
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