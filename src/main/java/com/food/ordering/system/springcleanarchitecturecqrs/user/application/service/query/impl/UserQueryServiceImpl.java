package com.food.ordering.system.springcleanarchitecturecqrs.user.application.service.query.impl;

import com.food.ordering.system.springcleanarchitecturecqrs.user.application.service.query.UserQueryService;
import com.food.ordering.system.springcleanarchitecturecqrs.user.application.usecase.query.FindUserByIdUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.user.application.usecase.query.FindUsersByEmailUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.dto.UserResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final FindUserByIdUseCase findUserByIdUseCase;
    private final FindUsersByEmailUseCase findUsersByEmailUseCase;

    public UserQueryServiceImpl(
            FindUserByIdUseCase findUserByIdUseCase,
            FindUsersByEmailUseCase findUsersByEmailUseCase) {
        this.findUserByIdUseCase = findUserByIdUseCase;
        this.findUsersByEmailUseCase = findUsersByEmailUseCase;
    }

    @Override
    public Optional<UserResponseDTO> findUserById(Long id) {
        log.info("Finding user with id: {}", id);
        return findUserByIdUseCase.execute(id);
    }

    @Override
    public List<UserResponseDTO> findUsersByEmail(String email) {
        log.info("Finding users with email: {}", email);
        return findUsersByEmailUseCase.execute(email);
    }
}