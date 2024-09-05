package com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KafkaException extends RuntimeException {
    public KafkaException(String message) {
        super(message);
        log.error(message);
    }

    public KafkaException(String message, Throwable cause) {
        super(message, cause);
        log.error(message, cause);
    }
}