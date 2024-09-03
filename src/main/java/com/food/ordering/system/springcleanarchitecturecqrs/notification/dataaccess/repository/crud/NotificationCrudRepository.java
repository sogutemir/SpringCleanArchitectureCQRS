package com.food.ordering.system.springcleanarchitecturecqrs.notification.dataaccess.repository.crud;

import com.food.ordering.system.springcleanarchitecturecqrs.notification.domain.entity.Notification;
import org.springframework.data.repository.CrudRepository;

public interface NotificationCrudRepository extends CrudRepository<Notification, Long> {

}