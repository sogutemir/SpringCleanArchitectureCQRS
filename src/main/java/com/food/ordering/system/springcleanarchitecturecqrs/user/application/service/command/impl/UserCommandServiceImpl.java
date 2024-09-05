package com.food.ordering.system.springcleanarchitecturecqrs.user.application.service.command.impl;

import com.food.ordering.system.springcleanarchitecturecqrs.user.application.service.command.UserCommandService;
import com.food.ordering.system.springcleanarchitecturecqrs.user.application.usecase.command.CreateUserUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.user.application.usecase.command.DeleteUserUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.user.application.usecase.command.UpdateUserUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    public UserCommandServiceImpl(
            CreateUserUseCase createUserUseCase,
            UpdateUserUseCase updateUserUseCase,
            DeleteUserUseCase deleteUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
    }

    @Override
    public UserDto createUser(UserDto userDTO) {
        log.info("Creating a new user with email: {}", userDTO.getEmail());
        return createUserUseCase.execute(userDTO);
    }

    @Override
    public Optional<UserDto> updateUser(Long id, UserDto userDTO) {
        log.info("Updating user with id: {}", id);
        return updateUserUseCase.execute(id, userDTO);
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Deleting user with id: {}", id);
        deleteUserUseCase.execute(id);
    }
}