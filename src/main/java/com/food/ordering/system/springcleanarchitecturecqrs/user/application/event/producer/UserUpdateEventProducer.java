package com.food.ordering.system.springcleanarchitecturecqrs.user.application.event.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.config.KafkaConfig;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.exception.KafkaMessageSendException;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.exception.KafkaSerializationException;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.event.UserUpdateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserUpdateEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final KafkaConfig kafkaConfig;

    public UserUpdateEventProducer(KafkaTemplate<String, Object> kafkaTemplate, ObjectMapper objectMapper, KafkaConfig kafkaConfig) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.kafkaConfig = kafkaConfig;
    }

    public void sendUserUpdateEvent(UserUpdateEvent userUpdateEvent) {
        try {
            log.info("Sending userUpdate event: {}", userUpdateEvent);
            String userUpdateJson = objectMapper.writeValueAsString(userUpdateEvent);
            kafkaTemplate.send(kafkaConfig.getUserUpdateTopic(), userUpdateJson);
            log.info("userUpdate event sent successfully");
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize order event to JSON", e);
            throw new KafkaSerializationException("Failed to serialize userUpdate event to JSON", e);
        } catch (Exception e) {
            throw new KafkaMessageSendException("Failed to send userUpdate event message", e);
        }
    }
}
