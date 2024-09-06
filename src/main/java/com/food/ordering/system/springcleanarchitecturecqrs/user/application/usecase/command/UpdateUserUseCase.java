package com.food.ordering.system.springcleanarchitecturecqrs.user.application.usecase.command;

import com.food.ordering.system.springcleanarchitecturecqrs.user.application.exception.UserNotFoundException;
import com.food.ordering.system.springcleanarchitecturecqrs.user.dataaccess.adapter.UserPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.dto.UserDto;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.event.UserUpdateEvent;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.mapper.UserMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.mapper.UserUpdateEventToUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class UpdateUserUseCase {

    private final UserPersistenceAdapter userPersistenceAdapter;

    public UpdateUserUseCase(UserPersistenceAdapter userPersistenceAdapter) {
        this.userPersistenceAdapter = userPersistenceAdapter;
    }

    public Optional<UserDto> execute(Long id, UserDto updatedUserDto) {
        try {
            log.info("Updating user with id: {}", id);
            Optional<User> existingUser = userPersistenceAdapter.findById(id);

            if (existingUser.isPresent()) {
                User user = existingUser.get();
                UserMapper.partialUpdate(updatedUserDto, user);
                User updatedUser = userPersistenceAdapter.save(user);
                log.info("User updated successfully with id: {}", updatedUser.getId());
                return Optional.of(UserMapper.toDTO(updatedUser));
            } else {
                throw new UserNotFoundException(id);
            }
        } catch (UserNotFoundException e) {
            log.error("User with id {} not found.", id);
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while updating user with id: {}", id, e);
            throw e;
        }
    }

    public void execute(UserUpdateEvent userUpdateEvent) {
        try {
            log.info("Updating user with id: {}", userUpdateEvent.getUserId());
            Optional<User> existingUser = userPersistenceAdapter.findById(userUpdateEvent.getUserId());

            if (existingUser.isPresent()) {
                User user = existingUser.get();
                UserUpdateEventToUserMapper.updateUser(userUpdateEvent, user);
                userPersistenceAdapter.save(user);
                log.info("User updated successfully with id: {}", user.getId());
            } else {
                throw new UserNotFoundException(userUpdateEvent.getUserId());
            }
        } catch (UserNotFoundException e) {
            log.error("User with id {} not found.", userUpdateEvent.getUserId());
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while updating user with id: {}", userUpdateEvent.getUserId(), e);
            throw e;
        }
    }
}