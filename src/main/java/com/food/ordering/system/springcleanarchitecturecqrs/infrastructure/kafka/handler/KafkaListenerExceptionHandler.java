package com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.handler;

import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.exception.KafkaMessageSendException;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.exception.KafkaSerializationException;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.producer.DeadLetterQueueProducer;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.producer.KafkaExceptionListenerProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaListenerExceptionHandler {

    private final KafkaExceptionListenerProducer kafkaExceptionListenerProducer;
    private final DeadLetterQueueProducer deadLetterQueueProducer;

    public KafkaListenerExceptionHandler(KafkaExceptionListenerProducer kafkaExceptionListenerProducer,
                                         DeadLetterQueueProducer deadLetterQueueProducer) {
        this.kafkaExceptionListenerProducer = kafkaExceptionListenerProducer;
        this.deadLetterQueueProducer = deadLetterQueueProducer;
    }

    public void handleSerializationException(String topic, String message, Exception e) {
        log.error("Serialization error occurred in Kafka listener: {}", e.getMessage());
        KafkaSerializationException exception = new KafkaSerializationException("Failed to deserialize Kafka message", e);
        kafkaExceptionListenerProducer.sendException(exception);
        deadLetterQueueProducer.sendToDlq(topic, message, "Serialization error: " + e.getMessage());
    }

    public void handleMessageProcessingException(String topic, String message, Exception e) {
        log.error("Error processing Kafka message: {}", e.getMessage());
        KafkaMessageSendException exception = new KafkaMessageSendException("Failed to process Kafka message", e);
        kafkaExceptionListenerProducer.sendException(exception);
        deadLetterQueueProducer.sendToDlq(topic, message, "Processing error: " + e.getMessage());
    }
}