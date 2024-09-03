package com.food.ordering.system.springcleanarchitecturecqrs.user.application.usecase.command;

import com.food.ordering.system.springcleanarchitecturecqrs.user.dataaccess.adapter.UserPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.dto.UserDTO;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class CreateUserUseCase {

    private final UserPersistenceAdapter userPersistenceAdapter;

    public CreateUserUseCase(UserPersistenceAdapter userPersistenceAdapter) {
        this.userPersistenceAdapter = userPersistenceAdapter;
    }

    public UserDTO execute(UserDTO userDTO) {
        try {
            log.info("Creating user with email: {}", userDTO.getEmail());
            User user = UserMapper.toEntity(userDTO);
            User savedUser = userPersistenceAdapter.save(user);
            log.info("User created successfully with id: {}", savedUser.getId());
            return UserMapper.toDTO(savedUser);
        } catch (Exception e) {
            log.error("Error occurred while creating user with email: {}", userDTO.getEmail(), e);
            throw e;
        }
    }
}