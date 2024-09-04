package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.exception;


import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(Long userId, BigDecimal amount) {
        super(String.format("User with ID %d has insufficient funds for the payment of amount %s", userId, amount));
        log.error("Insufficient funds for userId: {}, amount: {}", userId, amount);
    }
}
