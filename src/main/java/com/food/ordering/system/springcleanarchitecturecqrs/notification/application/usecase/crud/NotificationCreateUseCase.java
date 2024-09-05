package com.food.ordering.system.springcleanarchitecturecqrs.notification.application.usecase.crud;

import com.food.ordering.system.springcleanarchitecturecqrs.notification.dataaccess.adapter.NotificationPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.dto.NotificationDto;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.mapper.NotificationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationCreateUseCase {

    private final NotificationPersistenceAdapter notificationPersistenceAdapter;

    public NotificationCreateUseCase(NotificationPersistenceAdapter notificationPersistenceAdapter) {
        this.notificationPersistenceAdapter = notificationPersistenceAdapter;
    }

    public void execute(NotificationDto notificationDto){
        log.info("Notification create use case started. NotificationDTO: {}", notificationDto);

        try {
            notificationPersistenceAdapter.save(NotificationMapper.toEntity(notificationDto));
        } catch (Exception e) {
            log.error("Error occurred while saving notification: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save notification", e);
        }
    }
}
