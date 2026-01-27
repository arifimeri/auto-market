package com.example.automarket.exception.userExeption;

import org.springframework.http.HttpStatus;

public record UserException(String message, HttpStatus httpStatus) {
}
