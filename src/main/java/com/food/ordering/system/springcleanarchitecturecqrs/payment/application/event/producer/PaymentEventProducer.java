package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.event.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.config.KafkaConfig;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.exception.KafkaSerializationException;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.exception.KafkaMessageSendException;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.event.PaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final KafkaConfig kafkaConfig;

    @Autowired
    public PaymentEventProducer(KafkaTemplate<String, Object> kafkaTemplate, ObjectMapper objectMapper, KafkaConfig kafkaConfig) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.kafkaConfig = kafkaConfig;
    }

    public void sendMessage(PaymentEvent paymentEvent) {
        try {
            log.info("Sending PaymentEvent event: {}", paymentEvent);
            String paymentEventJson = objectMapper.writeValueAsString(paymentEvent);
            kafkaTemplate.send(kafkaConfig.getPaymentCreateTopic(), paymentEventJson);
            log.info("Payment event sent successfully");
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize payment event to JSON", e);
            throw new KafkaSerializationException("Failed to serialize payment event to JSON", e);
        } catch (Exception e) {
            throw new KafkaMessageSendException("Failed to send payment event message", e);
        }
    }
}