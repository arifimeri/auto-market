package com.example.automarket.service.impl;

import com.example.automarket.dto.request.ChangePasswordRequest;
import com.example.automarket.dto.request.UserRequest;
import com.example.automarket.dto.response.UserResponse;
import com.example.automarket.exception.userExeption.UserNotFoundException;
import com.example.automarket.enums.Role;
import com.example.automarket.exception.userExeption.UsernameAlreadyExistsException;
import com.example.automarket.model.User;
import com.example.automarket.repository.UserRepository;
import com.example.automarket.service.UserService;
import com.example.automarket.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse getCurrentUser(String username) {
        User user = findByUsername(username);
        return mapper.toResponse(user);
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        User user = new User(
                request.getUsername(),
                encoder.encode(request.getPassword()),
                request.getRole() != null ? request.getRole() : Role.ROLE_USER
        );

        return mapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request, String currentUsername) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        User currentUser = findByUsername(currentUsername);
        boolean isAdmin = currentUser.getRole().equals(Role.ROLE_ADMIN);

        if (!isAdmin && !currentUser.getId().equals(id)) {
            throw new AccessDeniedException("You can only update your own account");
        }
        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(encoder.encode(request.getPassword()));
        }
        if (request.getRole() != null && isAdmin) {
            user.setRole(request.getRole());
        }
        return mapper.toResponse(userRepository.save(user));
    }


    @Override
    public void deleteUser(Long id, String currentUsername) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        User currentUser = findByUsername(currentUsername);
        boolean isAdmin = currentUser.getRole().equals(Role.ROLE_ADMIN);
        if (!isAdmin && !currentUser.getId().equals(id)) {
            throw new AccessDeniedException("You can only delete your own account");
        }
        userRepository.delete(user);
    }


    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }


    @Override
    public List<String> getRolesForUser(String username) {
        User user = findByUsername(username);
        return List.of(user.getRole().name());
    }

    @Override
    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException("Username is already taken!");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        return userRepository.save(user);
    }

    public void changePassword(Long id, ChangePasswordRequest request, String authUsername) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (!user.getUsername().equals(authUsername)) {
            throw new AccessDeniedException("Cannot change another user's password!");
        }
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password is not correct, please try again!");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

}
