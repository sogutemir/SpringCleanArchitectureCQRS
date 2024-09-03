package com.food.ordering.system.springcleanarchitecturecqrs.order.application.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderNotFoundException extends RuntimeException {
  public OrderNotFoundException(Long id) {
    super("Order with id " + id + " not found.");
    log.error("Order with id {} not found.", id);
  }
}
