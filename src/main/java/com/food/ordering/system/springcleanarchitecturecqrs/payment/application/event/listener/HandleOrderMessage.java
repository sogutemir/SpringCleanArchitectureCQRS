package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.event.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.handler.KafkaListenerExceptionHandler;
import com.food.ordering.system.springcleanarchitecturecqrs.order.domain.event.OrderEvent;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.usecase.crud.PaymentCreateUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.dto.crud.PaymentDto;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.dto.factory.PaymentDtoFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HandleOrderMessage {

    private final PaymentCreateUseCase paymentCreateUseCase;
    private final ObjectMapper objectMapper;
    private final KafkaListenerExceptionHandler kafkaListenerExceptionHandler;

    public HandleOrderMessage(PaymentCreateUseCase paymentCreateUseCase, ObjectMapper objectMapper,
                              KafkaListenerExceptionHandler kafkaListenerExceptionHandler) {
        this.paymentCreateUseCase = paymentCreateUseCase;
        this.objectMapper = objectMapper;
        this.kafkaListenerExceptionHandler = kafkaListenerExceptionHandler;
    }

    @KafkaListener(topics = "${spring.kafka.topic.order}", groupId = "payment-group")
    public void listen(String orderMessage) {
        try {
            log.info("Received order message from Kafka: {}", orderMessage);
            OrderEvent orderEvent = objectMapper.readValue(orderMessage, OrderEvent.class);

            PaymentDto paymentDTO = PaymentDtoFactory.createPaymentDto(orderEvent);

            paymentCreateUseCase.execute(paymentDTO);
        } catch (JsonProcessingException e) {
            kafkaListenerExceptionHandler.handleSerializationException(e);
        } catch (Exception e) {
            kafkaListenerExceptionHandler.handleMessageProcessingException(e);
        }
    }
}
