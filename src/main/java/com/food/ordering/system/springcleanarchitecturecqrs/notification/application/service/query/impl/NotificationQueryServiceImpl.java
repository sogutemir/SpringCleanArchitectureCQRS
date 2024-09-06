package com.food.ordering.system.springcleanarchitecturecqrs.notification.application.service.query.impl;

import com.food.ordering.system.springcleanarchitecturecqrs.notification.application.service.query.NotificationQueryService;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.application.usecase.query.NotificationFindByUserIdUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.dto.NotificationResponseDto;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.entity.Notification;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.mapper.NotificationResponseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NotificationQueryServiceImpl implements NotificationQueryService {

    private final NotificationFindByUserIdUseCase notificationFindByUserIdUseCase;

    public NotificationQueryServiceImpl(NotificationFindByUserIdUseCase notificationFindByUserIdUseCase) {
        this.notificationFindByUserIdUseCase = notificationFindByUserIdUseCase;
    }

    @Override
    public List<NotificationResponseDto> findByUserId(Long userId) {
        log.info("Service: Finding notifications for user id: {}", userId);
        List<Notification> notifications = notificationFindByUserIdUseCase.execute(userId);
        return notifications.stream()
                .map(NotificationResponseMapper::toDto)
                .collect(Collectors.toList());
    }
}