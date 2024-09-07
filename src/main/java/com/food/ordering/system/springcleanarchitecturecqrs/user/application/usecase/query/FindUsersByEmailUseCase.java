package com.food.ordering.system.springcleanarchitecturecqrs.user.application.usecase.query;

import com.food.ordering.system.springcleanarchitecturecqrs.user.application.exception.UserNotFoundException;
import com.food.ordering.system.springcleanarchitecturecqrs.user.dataaccess.adapter.UserPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.dto.query.UserResponseDto;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.mapper.UserResponseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Component
public class FindUsersByEmailUseCase {

    private final UserPersistenceAdapter userPersistenceAdapter;

    public FindUsersByEmailUseCase(UserPersistenceAdapter userPersistenceAdapter) {
        this.userPersistenceAdapter = userPersistenceAdapter;
    }

    public List<UserResponseDto> execute(String email) {
        try {
            log.info("Finding users with email: {}", email);
            List<User> users = userPersistenceAdapter.findByEmail(email);
            if (users.isEmpty()) {
                throw new UserNotFoundException(email);
            }
            log.info("Found {} users with email: {}", users.size(), email);
            return users.stream()
                    .map(UserResponseMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (UserNotFoundException e) {
            log.error("No users found with email: {}", email);
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while finding users with email: {}", email, e);
            throw e;
        }
    }
}