package com.food.ordering.system.springcleanarchitecturecqrs.user.api.query;

import com.food.ordering.system.springcleanarchitecturecqrs.user.application.service.query.UserQueryService;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.dto.query.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users/query")
public class UserQueryController {

    private final UserQueryService userQueryService;

    public UserQueryController(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long id) {
        Optional<UserResponseDto> user = userQueryService.findUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findUsersByEmail(@RequestParam String email) {
        List<UserResponseDto> users = userQueryService.findUsersByEmail(email);
        return ResponseEntity.ok(users);
    }
}