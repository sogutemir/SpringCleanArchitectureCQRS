package com.food.ordering.system.springcleanarchitecturecqrs.user.application.usecase.message;

import com.food.ordering.system.springcleanarchitecturecqrs.user.application.event.producer.UserUpdateEventProducer;
import com.food.ordering.system.springcleanarchitecturecqrs.user.application.dto.event.UserUpdateEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SendUserUpdateEventUseCase {

    private final UserUpdateEventProducer userUpdateEventProducer;

    public SendUserUpdateEventUseCase(UserUpdateEventProducer userUpdateEventProducer) {
        this.userUpdateEventProducer = userUpdateEventProducer;
    }

    public void execute(UserUpdateEventDto userUpdateEventDto){
        try {
            log.info("Sending UserUpdate event for user id: {}", userUpdateEventDto.getUserId());
            userUpdateEventProducer.sendUserUpdateEvent(userUpdateEventDto);
            log.info("UserUpdate event sent successfully for user id: {}", userUpdateEventDto.getUserId());
        } catch (Exception kafkaException) {
            log.error("Error occurred while sending UserUpdate event for user id: {}. Error: {}", userUpdateEventDto.getUserId(), kafkaException.getMessage(), kafkaException);
        }
    }
}
