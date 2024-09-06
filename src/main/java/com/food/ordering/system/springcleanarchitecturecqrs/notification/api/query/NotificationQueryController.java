package com.food.ordering.system.springcleanarchitecturecqrs.notification.api.query;

import com.food.ordering.system.springcleanarchitecturecqrs.notification.application.service.query.NotificationQueryService;
import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.dto.NotificationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications/query")
public class NotificationQueryController {

    private final NotificationQueryService notificationQueryService;

    @GetMapping("/user/{userId}")
    public List<NotificationResponseDto> getNotificationsByUserId(@PathVariable Long userId) {
        return notificationQueryService.findByUserId(userId);
    }
}