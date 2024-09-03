package com.food.ordering.system.springcleanarchitecturecqrs.user.application.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("User with id " + id + " not found.");
        log.error("User with id {} not found.", id);
    }

    public UserNotFoundException(String email) {
        super("User with email " + email + " not found.");
        log.error("User with email {} not found.", email);
    }
}