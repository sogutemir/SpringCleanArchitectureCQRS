package com.food.ordering.system.springcleanarchitecturecqrs.order.application.event.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.handler.KafkaListenerExceptionHandler;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.usecase.command.UpdateOrderUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.dto.event.OrderUpdateEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HandleOrderUpdateMessage {

    private final ObjectMapper objectMapper;
    private final UpdateOrderUseCase updateOrderUseCase;
    private final KafkaListenerExceptionHandler kafkaListenerExceptionHandler;

    public HandleOrderUpdateMessage(ObjectMapper objectMapper, UpdateOrderUseCase updateOrderUseCase,
                                    KafkaListenerExceptionHandler kafkaListenerExceptionHandler) {
        this.objectMapper = objectMapper;
        this.updateOrderUseCase = updateOrderUseCase;
        this.kafkaListenerExceptionHandler = kafkaListenerExceptionHandler;
    }

    @KafkaListener(topics = "${spring.kafka.topic.order-update}", groupId = "order-update-group")
    public void listen(String orderUpdateMessage, Acknowledgment acknowledgment) {
        try {
            log.info("Received order update message from Kafka: {}", orderUpdateMessage);
            OrderUpdateEventDto orderUpdateEventDto = objectMapper.readValue(orderUpdateMessage, OrderUpdateEventDto.class);
            updateOrderUseCase.execute(orderUpdateEventDto.getOrderId(), orderUpdateEventDto);
            acknowledgment.acknowledge();
        } catch (JsonProcessingException e) {
            kafkaListenerExceptionHandler.handleSerializationException(e);
        } catch (Exception e) {
            kafkaListenerExceptionHandler.handleMessageProcessingException(e);
        }
    }

}