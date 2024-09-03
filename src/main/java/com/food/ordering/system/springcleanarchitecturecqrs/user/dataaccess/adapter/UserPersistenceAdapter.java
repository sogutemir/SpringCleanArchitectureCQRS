package com.food.ordering.system.springcleanarchitecturecqrs.user.dataaccess.adapter;

import com.food.ordering.system.springcleanarchitecturecqrs.user.dataaccess.repository.crud.UserCrudRepository;
import com.food.ordering.system.springcleanarchitecturecqrs.user.dataaccess.repository.query.UserQueryRepository;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserPersistenceAdapter {

    private final UserCrudRepository userCrudRepository;
    private final UserQueryRepository userQueryRepository;

    public UserPersistenceAdapter(UserCrudRepository userCrudRepository, UserQueryRepository userQueryRepository) {
        this.userCrudRepository = userCrudRepository;
        this.userQueryRepository = userQueryRepository;
    }

    public Optional<User> findById(Long id) {
        return userQueryRepository.findById(id);
    }

    public List<User> findByEmail(String email) {
        return userQueryRepository.findByEmail(email);
    }

    public List<User> findByNameContaining(String name) {
        return userQueryRepository.findByNameContaining(name);
    }

    public User save(User user) {
        return userCrudRepository.save(user);
    }

    public void deleteById(Long id) {
        userCrudRepository.deleteById(id);
    }
}