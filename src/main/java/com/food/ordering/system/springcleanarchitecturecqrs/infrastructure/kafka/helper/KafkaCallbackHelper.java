package com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.helper;

import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.handler.KafkaProducerExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class KafkaCallbackHelper {

    private final KafkaProducerExceptionHandler kafkaProducerExceptionHandler;
    private final RetryTemplate retryTemplate;

    public KafkaCallbackHelper(KafkaProducerExceptionHandler kafkaProducerExceptionHandler, RetryTemplate retryTemplate) {
        this.kafkaProducerExceptionHandler = kafkaProducerExceptionHandler;
        this.retryTemplate = retryTemplate;
    }

    public <T> void sendMessage(KafkaTemplate<String, Object> kafkaTemplate, String topic, T message, String partitionKey) {
        retryTemplate.execute(retryContext -> {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, partitionKey, message);

            future.thenAccept(result -> log.info("Message sent successfully: {}", result))
                    .exceptionally(ex -> {
                        log.error("Failed to send message", ex);
                        kafkaProducerExceptionHandler.handleSendException((Exception) ex);
                        return null;
                    });
            return null;
        }, recoveryContext -> {
            log.error("Failed to send message after retries.");
            return null;
        });
    }
}
