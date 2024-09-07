package com.food.ordering.system.springcleanarchitecturecqrs.notification.application.event.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.handler.KafkaListenerExceptionHandler;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.application.usecase.crud.NotificationCreateUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.application.dto.crud.NotificationDto;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.application.dto.factory.NotificationDtoFactory;
import com.food.ordering.system.springcleanarchitecturecqrs.product.application.dto.event.ProductNotificationEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HandleProductNotificationMessage {

    private final ObjectMapper objectMapper;
    private final NotificationCreateUseCase notificationCreateUseCase;
    private final KafkaListenerExceptionHandler kafkaListenerExceptionHandler;

    public HandleProductNotificationMessage(ObjectMapper objectMapper,
                                            NotificationCreateUseCase notificationCreateUseCase,
                                            KafkaListenerExceptionHandler kafkaListenerExceptionHandler) {
        this.objectMapper = objectMapper;
        this.notificationCreateUseCase = notificationCreateUseCase;
        this.kafkaListenerExceptionHandler = kafkaListenerExceptionHandler;
    }

    @KafkaListener(topics = "${spring.kafka.topic.product-notification}", groupId = "product-notification-group")
    public void listen(String productMessage, Acknowledgment acknowledgment) {
        try {
            log.info("Received product message from Kafka: {}", productMessage);
            ProductNotificationEventDto productNotificationEventDto = objectMapper.readValue(productMessage, ProductNotificationEventDto.class);

            NotificationDto notificationDto = NotificationDtoFactory.createProductNotificationDto(productNotificationEventDto);
            notificationCreateUseCase.execute(notificationDto);

            acknowledgment.acknowledge();
        } catch (JsonProcessingException e) {
            kafkaListenerExceptionHandler.handleSerializationException(e);
        } catch (Exception e) {
            kafkaListenerExceptionHandler.handleMessageProcessingException(e);
        }
    }
}
