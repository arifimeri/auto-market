package com.example.automarket.service;

import com.example.automarket.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User saveUser(User user);

    User editUser(Long id, User user);

    void deleteUser(Long id);

    boolean existsByUsername(String username);

    void save(User newUser);

    User findByUsername(String username);


}
