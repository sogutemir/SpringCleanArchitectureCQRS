package com.food.ordering.system.springcleanarchitecturecqrs.order.application.event.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.exception.KafkaSerializationException;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.command.UpdateOrderUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.event.OrderUpdateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HandleOrderUpdateMessage {

    private final ObjectMapper objectMapper;
    private final UpdateOrderUseCase updateOrderUseCase;

    public HandleOrderUpdateMessage(ObjectMapper objectMapper, UpdateOrderUseCase updateOrderUseCase) {
        this.objectMapper = objectMapper;
        this.updateOrderUseCase = updateOrderUseCase;
    }

    @KafkaListener(topics = "${spring.kafka.topic.order-update}", groupId = "order-update-group")
    public void listen(String orderUpdateMessage) {
        try {
            log.info("Received order update message from Kafka: {}", orderUpdateMessage);
            OrderUpdateEvent orderUpdateEvent = objectMapper.readValue(orderUpdateMessage, OrderUpdateEvent.class);
            updateOrderUseCase.execute(orderUpdateEvent.getOrderId(), orderUpdateEvent);
        } catch (JsonProcessingException e) {
            throw new KafkaSerializationException("Failed to deserialize order message", e);
        }
    }
}