package com.food.ordering.system.springcleanarchitecturecqrs.notification.application.usecase.crud;

import com.food.ordering.system.springcleanarchitecturecqrs.notification.dataaccess.adapter.NotificationPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.application.dto.crud.NotificationDto;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.application.mapper.NotificationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Slf4j
@Component
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
