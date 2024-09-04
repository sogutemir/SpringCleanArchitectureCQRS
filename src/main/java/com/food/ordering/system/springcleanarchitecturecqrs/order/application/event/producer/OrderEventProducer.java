package com.food.ordering.system.springcleanarchitecturecqrs.order.application.event.producer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.event.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper ;

    @Value("${spring.kafka.topic.order}")
    private String orderTopic;

    public OrderEventProducer(KafkaTemplate<String, Object> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendOrderEvent(OrderEvent orderEvent) throws JsonProcessingException {
        log.info("Sending order event: {}", orderEvent);
        String orderEventJson = objectMapper.writeValueAsString(orderEvent);
        kafkaTemplate.send(orderTopic, orderEventJson);
        log.info("Order event sent successfully");
    }
}