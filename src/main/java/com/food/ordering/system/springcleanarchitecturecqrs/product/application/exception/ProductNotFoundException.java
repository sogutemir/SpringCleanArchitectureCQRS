package com.food.ordering.system.springcleanarchitecturecqrs.product.application.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("product with id " + id + " not found.");
        log.error("product with id {} not found.", id);
    }

    public ProductNotFoundException(String message) {
        super(message);
        log.error(message);
    }

}
