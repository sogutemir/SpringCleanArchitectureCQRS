package com.food.ordering.system.springcleanarchitecturecqrs.user.application.service.query;

import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.dto.UserResponseDTO;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {

    Optional<UserResponseDTO> findUserById(Long id);

    List<UserResponseDTO> findUsersByEmail(String email);
}