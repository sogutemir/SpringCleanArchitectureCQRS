package com.food.ordering.system.springcleanarchitecturecqrs.user.application.service.command;

import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.dto.UserDTO;

import java.util.Optional;

public interface UserCommandService {

    UserDTO createUser(UserDTO userDTO);

    Optional<UserDTO> updateUser(Long id, UserDTO userDTO);

    void deleteUser(Long id);
}