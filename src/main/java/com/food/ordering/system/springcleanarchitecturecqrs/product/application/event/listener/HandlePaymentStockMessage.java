package com.food.ordering.system.springcleanarchitecturecqrs.product.application.event.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.handler.KafkaListenerExceptionHandler;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.usecase.command.StockUpdateUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.event.dto.StockUpdateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class HandlePaymentStockMessage {

    private final StockUpdateUseCase stockUpdateUseCase;
    private final ObjectMapper objectMapper;
    private final KafkaListenerExceptionHandler kafkaListenerExceptionHandler;

    public HandlePaymentStockMessage(StockUpdateUseCase stockUpdateUseCase,
                                     ObjectMapper objectMapper, KafkaListenerExceptionHandler kafkaListenerExceptionHandler) {
        this.stockUpdateUseCase = stockUpdateUseCase;
        this.objectMapper = objectMapper;
        this.kafkaListenerExceptionHandler = kafkaListenerExceptionHandler;
    }

    @KafkaListener(topics = "${spring.kafka.topic.stock-update}", groupId = "product-group")
    public void listen(String stockMessage) {
        try {
            log.info("Received stock message from Kafka: {}", stockMessage);
            StockUpdateEvent stockUpdateEvent = objectMapper.readValue(stockMessage, StockUpdateEvent.class);
            List<Long> productIds = new ArrayList<>(stockUpdateEvent.getProductQuantities().keySet());
            stockUpdateUseCase.execute(productIds, stockUpdateEvent.getProductQuantities());
        } catch (JsonProcessingException e) {
            kafkaListenerExceptionHandler.handleSerializationException(e);
        } catch (Exception e) {
            kafkaListenerExceptionHandler.handleMessageProcessingException(e);
        }
    }

}
