package com.food.ordering.system.springcleanarchitecturecqrs.notification.application.service.query;

import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.dto.NotificationResponseDto;

import java.util.List;

public interface NotificationQueryService {
    List<NotificationResponseDto> findByUserId(Long userId);
}