package com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.config.KafkaConfig;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.dto.DeadLetterQueueMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeadLetterQueueProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final KafkaConfig kafkaConfig;

    public DeadLetterQueueProducer(KafkaTemplate<String, String> kafkaTemplate,
                                   ObjectMapper objectMapper,
                                   KafkaConfig kafkaConfig) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.kafkaConfig = kafkaConfig;
    }

    public void sendToDlq(String originalTopic, String originalMessage, String errorMessage) {
        try {
            DeadLetterQueueMessage dlqMessage = new DeadLetterQueueMessage(originalTopic, originalMessage, errorMessage);
            String dlqMessageJson = objectMapper.writeValueAsString(dlqMessage);
            kafkaTemplate.send(kafkaConfig.getDeadLetterQueueTopic(), dlqMessageJson);
            log.info("Message sent to DLQ: {}", dlqMessageJson);
        } catch (Exception e) {
            log.error("Error sending message to DLQ", e);
        }
    }
}