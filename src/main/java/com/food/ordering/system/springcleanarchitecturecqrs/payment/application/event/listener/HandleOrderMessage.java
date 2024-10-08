package com.food.ordering.system.springcleanarchitecturecqrs.payment.application.event.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.handler.KafkaListenerExceptionHandler;
import com.food.ordering.system.springcleanarchitecturecqrs.order.application.dto.event.OrderCreateEventDto;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.usecase.crud.PaymentCreateUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.dto.crud.PaymentDto;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.dto.factory.PaymentDtoFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
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
    public void listen(String orderMessage, Acknowledgment acknowledgment) {
        try {
            log.info("Received order message from Kafka: {}", orderMessage);
            OrderCreateEventDto orderCreateEventDto = objectMapper.readValue(orderMessage, OrderCreateEventDto.class);

            PaymentDto paymentDTO = PaymentDtoFactory.createPaymentDto(orderCreateEventDto);

            paymentCreateUseCase.execute(paymentDTO);

            acknowledgment.acknowledge();
        } catch (JsonProcessingException e) {
            kafkaListenerExceptionHandler.handleSerializationException("${spring.kafka.topic.order}", orderMessage, e);
        } catch (Exception e) {
            kafkaListenerExceptionHandler.handleMessageProcessingException("${spring.kafka.topic.order}", orderMessage, e);
        }
    }
}
