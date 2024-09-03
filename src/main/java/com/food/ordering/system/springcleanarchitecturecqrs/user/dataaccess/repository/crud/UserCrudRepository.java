package com.food.ordering.system.springcleanarchitecturecqrs.user.dataaccess.repository.crud;


import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserCrudRepository extends CrudRepository<User, Long> {
    // CRUD işlemleri buradan yönetilecek
}