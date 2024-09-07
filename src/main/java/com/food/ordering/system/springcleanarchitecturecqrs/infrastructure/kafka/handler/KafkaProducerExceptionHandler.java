package com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.handler;

import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.exception.KafkaMessageSendException;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.exception.KafkaSerializationException;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.producer.KafkaExceptionProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaProducerExceptionHandler {

    private final KafkaExceptionProducer kafkaExceptionProducer;

    public KafkaProducerExceptionHandler(KafkaExceptionProducer kafkaExceptionProducer) {
        this.kafkaExceptionProducer = kafkaExceptionProducer;
    }

    public void handleSerializationException(Exception e) {
        log.error("Kafka serialization error occurred: {}", e.getMessage());
        kafkaExceptionProducer.sendException(new KafkaSerializationException("Serialization failed", e));
    }

    public void handleSendException(Exception e) {
        log.error("Kafka send message error occurred: {}", e.getMessage());
        kafkaExceptionProducer.sendException(new KafkaMessageSendException("Message sending failed", e));
    }
}
