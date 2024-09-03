package com.food.ordering.system.springcleanarchitecturecqrs.product.application.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String message) {
        super(message);
        log.error(message);
    }
}