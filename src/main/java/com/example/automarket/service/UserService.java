package com.example.automarket.service;

import com.example.automarket.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User saveUser(User user);

    User editUser(Long id, User user);

    void deleteUser(Long id);

    boolean existsByUsername(String username);

    User findByUsername(String username);

    List<String> getRolesForUser(String username);

    User registerUser(User user);
}
