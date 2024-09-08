package com.food.ordering.system.springcleanarchitecturecqrs.notification.application.event.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.handler.KafkaListenerExceptionHandler;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.application.usecase.crud.NotificationCreateUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.application.dto.factory.NotificationDtoFactory;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.dto.event.PaymentCreatedEventDto;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.application.usecase.query.PaymentFindByIdUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
public class HandlePaymentNotificationMessage {

    private final NotificationCreateUseCase notificationCreateUseCase;
    private final ObjectMapper objectMapper;
    private final KafkaListenerExceptionHandler kafkaListenerExceptionHandler;
    private final PaymentFindByIdUseCase paymentFindByIdUseCase;

    public HandlePaymentNotificationMessage(NotificationCreateUseCase notificationCreateUseCase,
                                            ObjectMapper objectMapper,
                                            KafkaListenerExceptionHandler kafkaListenerExceptionHandler,
                                            PaymentFindByIdUseCase paymentFindByIdUseCase) {
        this.notificationCreateUseCase = notificationCreateUseCase;
        this.objectMapper = objectMapper;
        this.kafkaListenerExceptionHandler = kafkaListenerExceptionHandler;
        this.paymentFindByIdUseCase = paymentFindByIdUseCase;
    }

    @KafkaListener(topics = "${spring.kafka.topic.payment-create}", groupId = "notification-group")
    public void listen(String paymentMessage, Acknowledgment acknowledgment) {
        log.info("Received payment message from Kafka: {}", paymentMessage);
        try {
            PaymentCreatedEventDto paymentCreatedEventDto = objectMapper.readValue(paymentMessage, PaymentCreatedEventDto.class);

            boolean paymentExists = paymentFindByIdUseCase.execute(paymentCreatedEventDto.getPaymentDTO().getPaymentId());
            if (!paymentExists) {
                log.warn("Payment with id {} not found, retrying later.", paymentCreatedEventDto.getPaymentDTO().getPaymentId());
                acknowledgment.nack(Duration.ofSeconds(1));
                return;
            }

            notificationCreateUseCase.execute(NotificationDtoFactory.createNotificationDto(paymentCreatedEventDto));
            acknowledgment.acknowledge();
        } catch (JsonProcessingException e) {
            kafkaListenerExceptionHandler.handleSerializationException("${spring.kafka.topic.payment-create}", paymentMessage, e);
        } catch (Exception e) {
            kafkaListenerExceptionHandler.handleMessageProcessingException("${spring.kafka.topic.payment-create}", paymentMessage, e);
        }
    }
}
