package com.food.ordering.system.springcleanarchitecturecqrs.user.application.usecase.query;

import com.food.ordering.system.springcleanarchitecturecqrs.user.application.exception.UserNotFoundException;
import com.food.ordering.system.springcleanarchitecturecqrs.user.dataaccess.adapter.UserPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.dto.UserResponseDto;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.mapper.UserResponseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class FindUserByIdUseCase {

    private final UserPersistenceAdapter userPersistenceAdapter;

    public FindUserByIdUseCase(UserPersistenceAdapter userPersistenceAdapter) {
        this.userPersistenceAdapter = userPersistenceAdapter;
    }

    public Optional<UserResponseDto> execute(Long id) {
        try {
            log.info("Finding user with id: {}", id);
            Optional<User> user = userPersistenceAdapter.findById(id);
            if (user.isPresent()) {
                log.info("User found with id: {}", id);
                return user.map(UserResponseMapper::toDTO);
            } else {
                throw new UserNotFoundException(id);
            }
        } catch (UserNotFoundException e) {
            log.error("User with id {} not found.", id);
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while finding user with id: {}", id, e);
            throw e;
        }
    }
}