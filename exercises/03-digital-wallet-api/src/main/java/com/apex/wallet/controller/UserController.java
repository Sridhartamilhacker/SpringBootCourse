package com.apex.wallet.controller;

import com.apex.wallet.dto.CreateUserRequest;
import com.apex.wallet.dto.UserSummaryResponse;
import com.apex.wallet.model.User;
import com.apex.wallet.repository.UserRepository;
import com.apex.wallet.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("{userId}")
    public ResponseEntity<User> getUserById(@PathVariable UUID userId){
        User user =  userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }
    @GetMapping("{userId}/summary")
    public ResponseEntity<UserSummaryResponse> getUserSummaryById(@PathVariable UUID userId){
        UserSummaryResponse user =  userService.findUserSummaryById(userId);
        return ResponseEntity.ok(user);
    }
    @GetMapping(value = "{userId}/fast", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getFastUSer(@PathVariable UUID userId){
        String rawJson = userRepository.findUserNativeJson(userId);
        return ResponseEntity.ok(rawJson);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid CreateUserRequest req){
        User newUser = userService.createUser(req);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();
        return ResponseEntity.created(location).body(newUser);
    }
}
