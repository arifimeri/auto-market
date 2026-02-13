package com.example.automarket.service;

import com.example.automarket.dto.request.ChangePasswordRequest;
import com.example.automarket.dto.request.UserRequest;
import com.example.automarket.dto.response.UserResponse;
import com.example.automarket.enums.Role;
import com.example.automarket.model.User;

import java.util.List;

public interface UserService {

    List<UserResponse> getAllUsers();

    UserResponse getCurrentUser(String username);

    UserResponse createUser(UserRequest request);

    UserResponse updateUser(Long id, UserRequest request, String currentUsername);

    void deleteUser(Long id, String currentUsername);

    boolean existsByUsername(String username);

    User findByUsername(String username);

    List<String> getRolesForUser(String username);

    User registerUser(User user);

    void changePassword(Long id, ChangePasswordRequest request, String authUsername);
}
