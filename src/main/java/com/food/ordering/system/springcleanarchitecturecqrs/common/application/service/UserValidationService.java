package com.food.ordering.system.springcleanarchitecturecqrs.common.application.service;

import com.food.ordering.system.springcleanarchitecturecqrs.user.dataaccess.adapter.UserPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserValidationService {

    private final UserPersistenceAdapter userPersistenceAdapter;

    public UserValidationService(UserPersistenceAdapter userPersistenceAdapter) {
        this.userPersistenceAdapter = userPersistenceAdapter;
    }

    public User validateUserExists(Long userId) {
        log.info("Validating user existence for userId: {}", userId);
        return userPersistenceAdapter.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", userId);
                    return new IllegalArgumentException("User not found with id: " + userId);
                });
    }
}