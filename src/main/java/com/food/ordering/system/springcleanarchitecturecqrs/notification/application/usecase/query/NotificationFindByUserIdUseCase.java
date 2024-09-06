package com.food.ordering.system.springcleanarchitecturecqrs.notification.application.usecase.query;

import com.food.ordering.system.springcleanarchitecturecqrs.notification.dataaccess.adapter.NotificationPersistenceAdapter;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.entity.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class NotificationFindByUserIdUseCase {

    private final NotificationPersistenceAdapter notificationPersistenceAdapter;

    public NotificationFindByUserIdUseCase(NotificationPersistenceAdapter notificationPersistenceAdapter) {
        this.notificationPersistenceAdapter = notificationPersistenceAdapter;
    }

    public List<Notification> execute(Long userId) {
        log.info("Finding notifications for user id: {}", userId);
        try {
            return notificationPersistenceAdapter.findByUserId(userId);
        } catch (Exception e) {
            log.error("Error occurred while finding notifications for user id: {}", userId, e);
            throw new RuntimeException("Failed to find notifications", e);
        }
    }
}