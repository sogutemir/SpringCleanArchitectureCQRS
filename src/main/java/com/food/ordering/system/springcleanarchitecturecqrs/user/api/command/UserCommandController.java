package com.food.ordering.system.springcleanarchitecturecqrs.user.api.command;

import com.food.ordering.system.springcleanarchitecturecqrs.user.application.service.command.UserCommandService;
import com.food.ordering.system.springcleanarchitecturecqrs.user.application.dto.crud.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users/command")
public class UserCommandController {

    private final UserCommandService userCommandService;

    public UserCommandController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDTO) {
        UserDto createdUser = userCommandService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDTO) {
        Optional<UserDto> updatedUser = userCommandService.updateUser(id, userDTO);
        return updatedUser.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userCommandService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}