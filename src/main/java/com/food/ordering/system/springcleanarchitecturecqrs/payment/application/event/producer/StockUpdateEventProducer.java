package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.event.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.config.KafkaConfig;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.exception.KafkaMessageSendException;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.exception.KafkaSerializationException;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.event.StockUpdateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StockUpdateEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final KafkaConfig kafkaConfig;

    public StockUpdateEventProducer(KafkaTemplate<String, Object> kafkaTemplate,
                                    ObjectMapper objectMapper, KafkaConfig kafkaConfig) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.kafkaConfig = kafkaConfig;
    }


    public void sendMessage(StockUpdateEvent stockUpdateEvent) {
        try {
            log.info("Sending StockUpdateEvent event: {}", stockUpdateEvent);
            String stockUpdateEventJson = objectMapper.writeValueAsString(stockUpdateEvent);
            kafkaTemplate.send(kafkaConfig.getStockUpdateTopic(), stockUpdateEventJson);
            log.info("StockUpdateEvent sent successfully");
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize StockUpdateEvent to JSON", e);
            throw new KafkaSerializationException("Failed to serialize StockUpdateEvent to JSON", e);
        } catch (Exception e) {
            throw new KafkaMessageSendException("Failed to send StockUpdateEvent message", e);
        }
    }
}
