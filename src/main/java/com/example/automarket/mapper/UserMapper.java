package com.example.automarket.mapper;

import com.example.automarket.dto.request.UserRequest;
import com.example.automarket.dto.response.UserResponse;
import com.example.automarket.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse toResponse(User user) {
        if (user == null) return null;
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }

    public User toEntity(UserRequest request, String encodedPassword) {
        if (request == null) return null;
        return new User(
                request.getUsername(),
                encodedPassword,
                request.getRole() != null ? request.getRole() : com.example.automarket.enums.Role.ROLE_USER
        );
    }

    public void updateEntity(User user, UserRequest request, String encodedPassword) {
        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(encodedPassword);
        }
        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }
    }
}
