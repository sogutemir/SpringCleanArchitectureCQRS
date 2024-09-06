package com.food.ordering.system.springcleanarchitecturecqrs.user.application.event.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.exception.KafkaSerializationException;
import com.food.ordering.system.springcleanarchitecturecqrs.user.application.usecase.command.UpdateUserUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.event.UserUpdateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HandleUserUpdateMessage {

    private final ObjectMapper objectMapper;
    private final UpdateUserUseCase updateUserUseCase;

    public HandleUserUpdateMessage(ObjectMapper objectMapper, UpdateUserUseCase updateUserUseCase) {
        this.objectMapper = objectMapper;
        this.updateUserUseCase = updateUserUseCase;
    }

    @KafkaListener(topics = "${spring.kafka.topic.user-update}", groupId = "user-update-group")
    public void listen(String userUpdateMessage) {
        try {
            log.info("Received payment message from Kafka: {}", userUpdateMessage);
            UserUpdateEvent userUpdateEvent = objectMapper.readValue(userUpdateMessage, UserUpdateEvent.class);

            updateUserUseCase.execute(userUpdateEvent);

        } catch (JsonProcessingException e) {
            throw new KafkaSerializationException("Failed to deserialize order message", e);
        }
    }

}
