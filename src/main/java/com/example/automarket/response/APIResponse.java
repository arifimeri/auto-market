package com.example.automarket.response;

import org.springframework.http.HttpStatus;

public record APIResponse(String message, HttpStatus status) {
}
