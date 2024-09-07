package com.food.ordering.system.springcleanarchitecturecqrs.user.application.service.command;

import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.dto.crud.UserDto;

import java.util.Optional;

public interface UserCommandService {

    UserDto createUser(UserDto userDTO);

    Optional<UserDto> updateUser(Long id, UserDto userDTO);

    void deleteUser(Long id);
}