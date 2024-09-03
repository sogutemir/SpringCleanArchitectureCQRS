package com.food.ordering.system.springcleanarchitecturecqrs.user.application.usecase.crud;

import com.food.ordering.system.springcleanarchitecturecqrs.user.application.exception.UserNotFoundException;
import com.food.ordering.system.springcleanarchitecturecqrs.user.dataaccess.adapter.UserPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.dto.UserDTO;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.mapper.UserMapper;
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

    public Optional<UserDTO> execute(Long id, UserDTO updatedUserDTO) {
        try {
            log.info("Updating user with id: {}", id);
            Optional<User> existingUser = userPersistenceAdapter.findById(id);

            if (existingUser.isPresent()) {
                User user = existingUser.get();
                UserMapper.partialUpdate(updatedUserDTO, user);
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
}