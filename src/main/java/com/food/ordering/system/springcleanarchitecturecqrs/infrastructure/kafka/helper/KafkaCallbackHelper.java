package com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.helper;

import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.handler.KafkaProducerExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class KafkaCallbackHelper {

    private final KafkaProducerExceptionHandler kafkaProducerExceptionHandler;

    public KafkaCallbackHelper(KafkaProducerExceptionHandler kafkaProducerExceptionHandler) {
        this.kafkaProducerExceptionHandler = kafkaProducerExceptionHandler;
    }

    public <T> void sendMessage(KafkaTemplate<String, Object> kafkaTemplate, String topic, T message) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, message);

        future.thenAccept(result -> log.info("Message sent successfully: {}", result))
                .exceptionally(ex -> {
                    log.error("Failed to send message", ex);
                    kafkaProducerExceptionHandler.handleSendException((Exception) ex);
                    return null;
                });
    }
}