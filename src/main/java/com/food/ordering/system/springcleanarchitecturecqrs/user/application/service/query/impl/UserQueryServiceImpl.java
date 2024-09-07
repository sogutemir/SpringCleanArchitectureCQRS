package com.food.ordering.system.springcleanarchitecturecqrs.user.application.service.query.impl;

import com.food.ordering.system.springcleanarchitecturecqrs.user.application.service.query.UserQueryService;
import com.food.ordering.system.springcleanarchitecturecqrs.user.application.usecase.query.FindUserByIdUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.user.application.usecase.query.FindUsersByEmailUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.dto.query.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<UserResponseDto> findUserById(Long id) {
        return findUserByIdUseCase.execute(id);
    }

    @Override
    public List<UserResponseDto> findUsersByEmail(String email) {
        return findUsersByEmailUseCase.execute(email);
    }
}