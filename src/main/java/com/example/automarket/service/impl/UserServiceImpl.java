package com.example.automarket.service.impl;

import com.example.automarket.exception.userExeption.UserNotFoundException;
import com.example.automarket.enums.Role;
import com.example.automarket.exception.userExeption.UsernameAlreadyExistsException;
import com.example.automarket.model.User;
import com.example.automarket.repository.UserRepository;
import com.example.automarket.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User editUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found!"));

        checkOwnership(existingUser);

        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(encoder.encode(user.getPassword()));
        existingUser.setRole(user.getRole());

        return userRepository.save(existingUser);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found!"));

        checkOwnership(existingUser);
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username " + username + " doesn't exists!"));
    }

    @Override
    public List<String> getRolesForUser(String username) {
        User user = findByUsername(username);
        return List.of(user.getRole().name());
    }

    @Override
    public User registerUser(User user) {
        if (existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException("Username is already taken!");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        return userRepository.save(user);
    }

    private void checkOwnership(User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) return;

        if (!user.getUsername().equals(auth.getName())) {
            throw new AccessDeniedException("You have no right to modify this user!");
        }
    }
}
