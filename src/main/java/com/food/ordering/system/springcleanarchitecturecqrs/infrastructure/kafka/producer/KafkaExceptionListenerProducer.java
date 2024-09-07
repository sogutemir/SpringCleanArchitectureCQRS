package com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.config.KafkaConfig;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.exception.KafkaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaExceptionListenerProducer {

    private final ObjectMapper objectMapper;
    private final KafkaConfig kafkaConfig;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaExceptionListenerProducer(ObjectMapper objectMapper, KafkaConfig kafkaConfig, KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.kafkaConfig = kafkaConfig;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendException(KafkaException exception) {
        try {
            String exceptionMessage = objectMapper.writeValueAsString(exception.getMessage());
            kafkaTemplate.send(kafkaConfig.getKafkaListenerExceptionTopic(), exceptionMessage);
            log.info("Exception message sent successfully");
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize exception message to JSON", e);
        } catch (Exception e) {
            log.error("Failed to send exception message", e);
        }
    }
}
