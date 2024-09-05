package com.food.ordering.system.springcleanarchitecturecqrs.user.application.service.query;

import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.dto.UserResponseDto;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {

    Optional<UserResponseDto> findUserById(Long id);

    List<UserResponseDto> findUsersByEmail(String email);
}