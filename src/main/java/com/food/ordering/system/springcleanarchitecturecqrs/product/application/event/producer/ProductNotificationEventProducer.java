package com.food.ordering.system.springcleanarchitecturecqrs.product.application.event.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.config.KafkaConfig;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.exception.KafkaMessageSendException;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.exception.KafkaSerializationException;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.event.ProductNotificationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductNotificationEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final KafkaConfig kafkaConfig;

    public ProductNotificationEventProducer(KafkaTemplate<String, Object> kafkaTemplate, ObjectMapper objectMapper, KafkaConfig kafkaConfig) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.kafkaConfig = kafkaConfig;
    }

    public void sendMessage(ProductNotificationEvent productNotificationEvent) {
        try {
            log.info("Sending ProductNotificationEvent event: {}", productNotificationEvent);
            String productNotificationEventJson = objectMapper.writeValueAsString(productNotificationEvent);
            kafkaTemplate.send(kafkaConfig.getProductNotificationTopic(), productNotificationEventJson);
            log.info("ProductNotificationEvent sent successfully");
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize ProductNotificationEvent to JSON", e);
            throw new KafkaSerializationException("Failed to serialize ProductNotificationEvent to JSON", e);
        } catch (Exception e) {
            throw new KafkaMessageSendException("Failed to send ProductNotificationEvent message", e);
        }
    }
}
