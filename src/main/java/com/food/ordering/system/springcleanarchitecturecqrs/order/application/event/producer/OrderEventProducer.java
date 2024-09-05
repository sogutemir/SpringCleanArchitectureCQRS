package com.food.ordering.system.springcleanarchitecturecqrs.order.application.event.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.config.KafkaConfig;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.exception.KafkaSerializationException;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.exception.KafkaMessageSendException;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.event.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final KafkaConfig kafkaConfig;

    public OrderEventProducer(KafkaTemplate<String, Object> kafkaTemplate, ObjectMapper objectMapper, KafkaConfig kafkaConfig) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.kafkaConfig = kafkaConfig;
    }

    public void sendOrderEvent(OrderEvent orderEvent) {
        try {
            log.info("Sending order event: {}", orderEvent);
            String orderEventJson = objectMapper.writeValueAsString(orderEvent);
            kafkaTemplate.send(kafkaConfig.getOrderTopic(), orderEventJson);
            log.info("Order event sent successfully");
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize order event to JSON", e);
            throw new KafkaSerializationException("Failed to serialize order event to JSON", e);
        } catch (Exception e) {
            throw new KafkaMessageSendException("Failed to send order event message", e);
        }
    }
}