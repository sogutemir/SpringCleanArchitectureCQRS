package com.food.ordering.system.springcleanarchitecturecqrs.user.application.usecase.message;

import com.food.ordering.system.springcleanarchitecturecqrs.user.application.event.producer.UserUpdateEventProducer;
import com.food.ordering.system.springcleanarchitecturecqrs.user.application.event.model.UserUpdateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SendUserUpdateEventUseCase {

    private final UserUpdateEventProducer userUpdateEventProducer;

    public SendUserUpdateEventUseCase(UserUpdateEventProducer userUpdateEventProducer) {
        this.userUpdateEventProducer = userUpdateEventProducer;
    }

    public void execute(UserUpdateEvent userUpdateEvent){
        try {
            log.info("Sending UserUpdate event for user id: {}", userUpdateEvent.getUserId());
            userUpdateEventProducer.sendUserUpdateEvent(userUpdateEvent);
            log.info("UserUpdate event sent successfully for user id: {}", userUpdateEvent.getUserId());
        } catch (Exception kafkaException) {
            log.error("Error occurred while sending UserUpdate event for user id: {}. Error: {}", userUpdateEvent.getUserId(), kafkaException.getMessage(), kafkaException);
        }
    }
}
