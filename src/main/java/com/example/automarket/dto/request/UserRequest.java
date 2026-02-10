package com.example.automarket.dto.request;

import com.example.automarket.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserRequest {
    private String username;
    private String password;
    private Role role;
}
