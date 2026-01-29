package com.example.automarket.controller;

import com.example.automarket.model.User;
import com.example.automarket.security.JwtUtil;
import com.example.automarket.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtils;

    @PostMapping("/login")
    public Map<String, Object> authenticateUser(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userService.getRolesForUser(userDetails.getUsername());
        return Map.of(
                "username", userDetails.getUsername(),
                "roles", roles,
                "token", jwtUtils.generateToken(userDetails.getUsername(), roles)
        );
    }

    @PostMapping("/signup")
    public Map<String, Object> registerUser(@RequestBody User user) {
        User newUser = userService.registerUser(user);

        List<String> roles = userService.getRolesForUser(newUser.getUsername());

        return Map.of(
                "username", newUser.getUsername(),
                "roles", roles,
                "token", jwtUtils.generateToken(newUser.getUsername(), roles)
        );
    }
}
