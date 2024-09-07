package com.food.ordering.system.springcleanarchitecturecqrs.user.application.event.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.handler.KafkaListenerExceptionHandler;
import com.food.ordering.system.springcleanarchitecturecqrs.user.application.usecase.command.UpdateUserUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.user.application.dto.event.UserUpdateEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HandleUserUpdateMessage {

    private final ObjectMapper objectMapper;
    private final UpdateUserUseCase updateUserUseCase;
    private final KafkaListenerExceptionHandler kafkaListenerExceptionHandler;

    public HandleUserUpdateMessage(ObjectMapper objectMapper, UpdateUserUseCase updateUserUseCase, KafkaListenerExceptionHandler kafkaListenerExceptionHandler) {
        this.objectMapper = objectMapper;
        this.updateUserUseCase = updateUserUseCase;
        this.kafkaListenerExceptionHandler = kafkaListenerExceptionHandler;
    }

    @KafkaListener(topics = "${spring.kafka.topic.user-update}", groupId = "user-update-group")
    public void listen(String userUpdateMessage, Acknowledgment acknowledgment) {
        try {
            log.info("Received user update message from Kafka: {}", userUpdateMessage);
            UserUpdateEventDto userUpdateEventDto = objectMapper.readValue(userUpdateMessage, UserUpdateEventDto.class);
            updateUserUseCase.execute(userUpdateEventDto);
            acknowledgment.acknowledge();
        } catch (JsonProcessingException e) {
            kafkaListenerExceptionHandler.handleSerializationException(e);
        } catch (Exception e) {
            kafkaListenerExceptionHandler.handleMessageProcessingException(e);
        }
    }
}
