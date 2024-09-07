package com.food.ordering.system.springcleanarchitecturecqrs.order.application.event.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.config.KafkaConfig;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.handler.KafkaProducerExceptionHandler;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.event.dto.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final KafkaConfig kafkaConfig;
    private final KafkaProducerExceptionHandler kafkaProducerExceptionHandler;

    public OrderEventProducer(KafkaTemplate<String, Object> kafkaTemplate,
                              ObjectMapper objectMapper, KafkaConfig kafkaConfig,
                              KafkaProducerExceptionHandler kafkaProducerExceptionHandler) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.kafkaConfig = kafkaConfig;
        this.kafkaProducerExceptionHandler = kafkaProducerExceptionHandler;
    }

    public void sendOrderEvent(OrderEvent orderEvent) {
        try {
            log.info("Sending order event: {}", orderEvent);
            String orderEventJson = objectMapper.writeValueAsString(orderEvent);
            kafkaTemplate.send(kafkaConfig.getOrderTopic(), orderEventJson);
            log.info("Order event sent successfully");
        } catch (JsonProcessingException e) {
            kafkaProducerExceptionHandler.handleSerializationException(e);
        } catch (Exception e) {
            kafkaProducerExceptionHandler.handleSendException(e);
        }
    }
}
