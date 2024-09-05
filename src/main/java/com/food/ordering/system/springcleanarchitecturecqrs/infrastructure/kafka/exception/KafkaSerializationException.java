package com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KafkaSerializationException extends KafkaException {
    public KafkaSerializationException(String message) {
        super(message);
        log.error(message);
    }

    public KafkaSerializationException(String message, Throwable cause) {
        super(message, cause);
        log.error(message, cause);
    }
}