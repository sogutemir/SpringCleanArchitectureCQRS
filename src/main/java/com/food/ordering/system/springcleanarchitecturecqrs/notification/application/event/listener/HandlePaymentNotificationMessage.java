package com.food.ordering.system.springcleanarchitecturecqrs.notification.application.event.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.exception.KafkaSerializationException;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.application.usecase.crud.NotificationCreateUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.dto.NotificationDto;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.enums.NotificationStatus;
import com.food.ordering.system.springcleanarchitecturecqrs.payment.domain.event.PaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HandlePaymentNotificationMessage {

    private final NotificationCreateUseCase notificationCreateUseCase;
    private final ObjectMapper objectMapper;

    public HandlePaymentNotificationMessage(NotificationCreateUseCase notificationCreateUseCase, ObjectMapper objectMapper) {
        this.notificationCreateUseCase = notificationCreateUseCase;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${spring.kafka.topic.payment-create}", groupId = "notification-group")
    public void listen(String paymentMessage) {
        try {
            log.info("Received payment message from Kafka: {}", paymentMessage);
            PaymentEvent paymentEvent = objectMapper.readValue(paymentMessage, PaymentEvent.class);
            NotificationDto.NotificationDtoBuilder notificationBuilder = NotificationDto.builder()
                .orderId(paymentEvent.getPaymentDTO().getOrderId())
                .userId(paymentEvent.getPaymentDTO().getUserId());

            if (paymentEvent.getPaymentDTO().getPaymentId() != null) {
                notificationBuilder.paymentId(paymentEvent.getPaymentDTO().getPaymentId())
                                   .message("Payment has been created successfully")
                                   .status(NotificationStatus.SENT);

            } else {
                notificationBuilder.message("Payment could not be made due to insufficient funds")
                                   .status(NotificationStatus.SENT);
            }

            NotificationDto notificationDTO = notificationBuilder.build();

            notificationCreateUseCase.execute(notificationDTO);

        } catch (JsonProcessingException e) {
            throw new KafkaSerializationException("Failed to deserialize order message", e);
        }
    }
}
