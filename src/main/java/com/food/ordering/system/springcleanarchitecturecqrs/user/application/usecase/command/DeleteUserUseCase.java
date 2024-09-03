package com.food.ordering.system.springcleanarchitecturecqrs.user.application.usecase.command;

import com.food.ordering.system.springcleanarchitecturecqrs.user.application.exception.UserNotFoundException;
import com.food.ordering.system.springcleanarchitecturecqrs.user.dataaccess.adapter.UserPersistenceAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeleteUserUseCase {

    private final UserPersistenceAdapter userPersistenceAdapter;

    public DeleteUserUseCase(UserPersistenceAdapter userPersistenceAdapter) {
        this.userPersistenceAdapter = userPersistenceAdapter;
    }

    public void execute(Long id) {
        try {
            log.info("Deleting user with id: {}", id);
            if (userPersistenceAdapter.findById(id).isPresent()) {
                userPersistenceAdapter.deleteById(id);
                log.info("User deleted successfully with id: {}", id);
            } else {
                throw new UserNotFoundException(id);
            }
        } catch (UserNotFoundException e) {
            log.error("User with id {} not found.", id);
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while deleting user with id: {}", id, e);
            throw e;
        }
    }
}