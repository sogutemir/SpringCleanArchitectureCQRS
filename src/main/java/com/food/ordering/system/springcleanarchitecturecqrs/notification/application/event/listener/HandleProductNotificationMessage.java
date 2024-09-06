package com.food.ordering.system.springcleanarchitecturecqrs.notification.application.event.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.exception.KafkaSerializationException;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.application.usecase.crud.NotificationCreateUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.dto.NotificationDto;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.enums.NotificationStatus;
import com.food.ordering.system.springcleanarchitecturecqrs.product.domain.event.ProductNotificationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HandleProductNotificationMessage {

    private final ObjectMapper objectMapper;
    private final NotificationCreateUseCase notificationCreateUseCase;

    public HandleProductNotificationMessage(ObjectMapper objectMapper, NotificationCreateUseCase notificationCreateUseCase) {
        this.objectMapper = objectMapper;
        this.notificationCreateUseCase = notificationCreateUseCase;
    }

    @KafkaListener(topics = "${spring.kafka.topic.product-notification}", groupId = "product-notification-group")
    public void listen(String productMessage) {
        try {
            log.info("Received product message from Kafka: {}", productMessage);
            ProductNotificationEvent productNotificationEvent = objectMapper.readValue(productMessage, ProductNotificationEvent.class);

            String message = productNotificationEvent.getMessage().isEmpty()
                    ? "Product purchased by: " + productNotificationEvent.getUserName()
                    : productNotificationEvent.getMessage() + ": " + productNotificationEvent.getProductId();

            NotificationDto notificationDto = NotificationDto.builder()
                    .productId(productNotificationEvent.getProductId())
                    .orderId(productNotificationEvent.getOrderId())
                    .userId(productNotificationEvent.getUserId())
                    .message(message)
                    .status(NotificationStatus.SENT)
                    .build();

            notificationCreateUseCase.execute(notificationDto);

        } catch (JsonProcessingException e) {
            throw new KafkaSerializationException("Failed to deserialize product message", e);
        }
    }
}