package com.food.ordering.system.springcleanarchitecturecqrs.user.application.service.command.impl;

import com.food.ordering.system.springcleanarchitecturecqrs.user.application.service.command.UserCommandService;
import com.food.ordering.system.springcleanarchitecturecqrs.user.application.usecase.command.CreateUserUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.user.application.usecase.command.DeleteUserUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.user.application.usecase.command.UpdateUserUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.dto.crud.UserDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        return createUserUseCase.execute(userDTO);
    }

    @Override
    public Optional<UserDto> updateUser(Long id, UserDto userDTO) {
        return updateUserUseCase.execute(id, userDTO);
    }

    @Override
    public void deleteUser(Long id) {
        deleteUserUseCase.execute(id);
    }
}