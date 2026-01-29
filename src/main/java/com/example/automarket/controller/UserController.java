package com.example.automarket.controller;

import com.example.automarket.model.User;
import com.example.automarket.response.APIResponse;
import com.example.automarket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse> createUser(@RequestBody User user) {
        User newUser = userService.saveUser(user);
        String message = "User with username " + newUser.getUsername() + " was created successfully.";
        return ResponseEntity.ok(new APIResponse(message, HttpStatus.OK));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse> editUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.editUser(id, user);
        String message = "User with id " + updatedUser.getId() + " was updated successfully.";
        return ResponseEntity.ok(new APIResponse(message, HttpStatus.OK));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        String message = "User with id " + id + " was deleted successfully.";
        return ResponseEntity.ok(new APIResponse(message, HttpStatus.OK));
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public User getMyProfile(Authentication authentication){
        String username = authentication.getName();
        return userService.findByUsername(username);
    }
}
