package com.food.ordering.system.springcleanarchitecturecqrs.user.application.usecase.validation;

import com.food.ordering.system.springcleanarchitecturecqrs.user.dataaccess.adapter.UserPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserValidationUseCase {

    private final UserPersistenceAdapter userPersistenceAdapter;

    public UserValidationUseCase(UserPersistenceAdapter userPersistenceAdapter) {
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