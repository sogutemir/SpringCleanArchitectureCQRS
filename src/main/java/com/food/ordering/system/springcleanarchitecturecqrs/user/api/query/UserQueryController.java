package com.food.ordering.system.springcleanarchitecturecqrs.user.api.query;

import com.food.ordering.system.springcleanarchitecturecqrs.user.application.service.query.UserQueryService;
import com.food.ordering.system.springcleanarchitecturecqrs.user.domain.dto.UserResponseDTO;
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
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable Long id) {
        Optional<UserResponseDTO> user = userQueryService.findUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findUsersByEmail(@RequestParam String email) {
        List<UserResponseDTO> users = userQueryService.findUsersByEmail(email);
        return ResponseEntity.ok(users);
    }
}