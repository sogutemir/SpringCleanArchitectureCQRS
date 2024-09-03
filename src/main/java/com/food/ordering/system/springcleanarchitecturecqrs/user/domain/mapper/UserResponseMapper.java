package com.food.ordering.system.springcleanarchitecturecqrs.user.domain.mapper;


import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.dto.UserResponseDTO;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;

public class UserResponseMapper {

    public static UserResponseDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .money(user.getMoney())
                .build();
    }
}