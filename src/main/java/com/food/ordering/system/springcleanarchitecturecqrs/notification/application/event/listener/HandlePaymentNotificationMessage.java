package com.food.ordering.system.springcleanarchitecturecqrs.notification.application.event.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.handler.KafkaListenerExceptionHandler;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.application.usecase.crud.NotificationCreateUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.application.dto.factory.NotificationDtoFactory;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.event.dto.PaymentCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HandlePaymentNotificationMessage {

    private final NotificationCreateUseCase notificationCreateUseCase;
    private final ObjectMapper objectMapper;
    private final KafkaListenerExceptionHandler kafkaListenerExceptionHandler;

    public HandlePaymentNotificationMessage(NotificationCreateUseCase notificationCreateUseCase,
                                            ObjectMapper objectMapper,
                                            KafkaListenerExceptionHandler kafkaListenerExceptionHandler) {
        this.notificationCreateUseCase = notificationCreateUseCase;
        this.objectMapper = objectMapper;
        this.kafkaListenerExceptionHandler = kafkaListenerExceptionHandler;
    }

    @KafkaListener(topics = "${spring.kafka.topic.payment-create}", groupId = "notification-group")
    public void listen(String paymentMessage, Acknowledgment acknowledgment) {
        try {
            log.info("Received payment message from Kafka: {}", paymentMessage);
            PaymentCreatedEvent paymentCreatedEvent = objectMapper.readValue(paymentMessage, PaymentCreatedEvent.class);

            notificationCreateUseCase.execute(NotificationDtoFactory.createNotificationDto(paymentCreatedEvent));

            acknowledgment.acknowledge();
        } catch (JsonProcessingException e) {
            kafkaListenerExceptionHandler.handleSerializationException(e);
        } catch (Exception e) {
            kafkaListenerExceptionHandler.handleMessageProcessingException(e);
        }
    }
}
