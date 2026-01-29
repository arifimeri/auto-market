package com.example.automarket.exception.vehicleException;

import org.springframework.http.HttpStatus;

public record VehicleException(String message, HttpStatus httpStatus) {
}
