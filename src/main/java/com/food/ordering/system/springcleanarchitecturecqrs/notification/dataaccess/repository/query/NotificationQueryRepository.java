package com.food.ordering.system.springcleanarchitecturecqrs.notification.dataaccess.repository.query;

import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationQueryRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId")
    List<Notification> findByUserId(@Param("userId") Long userId);

    @Query("SELECT n FROM Notification n WHERE n.message LIKE %:message%")
    List<Notification> findByMessageContaining(@Param("message") String message);
}