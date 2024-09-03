package com.food.ordering.system.springcleanarchitecturecqrs.notification.dataaccess.adapter;

import com.food.ordering.system.springcleanarchitecturecqrs.notification.dataaccess.repository.crud.NotificationCrudRepository;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.dataaccess.repository.query.NotificationQueryRepository;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.entity.Notification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class NotificationPersistenceAdapter {

    private final NotificationCrudRepository notificationCrudRepository;
    private final NotificationQueryRepository notificationQueryRepository;

    public NotificationPersistenceAdapter(NotificationCrudRepository notificationCrudRepository, NotificationQueryRepository notificationQueryRepository) {
        this.notificationCrudRepository = notificationCrudRepository;
        this.notificationQueryRepository = notificationQueryRepository;
    }

    public Optional<Notification> findById(Long id) {
        return notificationQueryRepository.findById(id);
    }

    public List<Notification> findByUserId(Long userId) {
        return notificationQueryRepository.findByUserId(userId);
    }

    public List<Notification> findByMessageContaining(String message) {
        return notificationQueryRepository.findByMessageContaining(message);
    }

    public Notification save(Notification notification) {
        return notificationCrudRepository.save(notification);
    }

    public void deleteById(Long id) {
        notificationCrudRepository.deleteById(id);
    }
}