package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.event.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.exception.KafkaSerializationException;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.event.OrderEvent;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.usecase.crud.PaymentCreateUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.dto.PaymentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@Transactional
public class HandleOrderMessage {

    private final PaymentCreateUseCase paymentCreateUseCase;
    private final ObjectMapper objectMapper;

    public HandleOrderMessage(PaymentCreateUseCase paymentCreateUseCase, ObjectMapper objectMapper) {
        this.paymentCreateUseCase = paymentCreateUseCase;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${spring.kafka.topic.order}", groupId = "payment-group")
    public void listen(String orderMessage) {
        try {
            log.info("Received order message from Kafka: {}", orderMessage);
            OrderEvent orderEvent = objectMapper.readValue(orderMessage, OrderEvent.class);

            PaymentDto paymentDTO = PaymentDto.builder()
                    .orderId(orderEvent.getOrderId())
                    .userId(orderEvent.getUserId())
                    .amount(orderEvent.getTotalAmount())
                    .build();

            paymentCreateUseCase.execute(paymentDTO);
        } catch (JsonProcessingException e) {
            throw new KafkaSerializationException("Failed to deserialize order message", e);
        }
    }
}