package com.food.ordering.system.springcleanarchitecturecqrs.notification.application.service.query.impl;

import com.food.ordering.system.springcleanarchitecturecqrs.notification.application.service.query.NotificationQueryService;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.application.usecase.query.NotificationFindByUserIdUseCase;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.application.dto.query.NotificationResponseDto;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.entity.Notification;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.application.mapper.NotificationResponseMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationQueryServiceImpl implements NotificationQueryService {

    private final NotificationFindByUserIdUseCase notificationFindByUserIdUseCase;

    public NotificationQueryServiceImpl(NotificationFindByUserIdUseCase notificationFindByUserIdUseCase) {
        this.notificationFindByUserIdUseCase = notificationFindByUserIdUseCase;
    }

    @Override
    public List<NotificationResponseDto> findByUserId(Long userId) {
        List<Notification> notifications = notificationFindByUserIdUseCase.execute(userId);
        return notifications.stream()
                .map(NotificationResponseMapper::toDto)
                .collect(Collectors.toList());
    }
}