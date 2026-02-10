package com.example.automarket.exception;

import org.springframework.web.bind.annotation.RestController;

import com.example.automarket.response.APIResponse;
import com.example.automarket.exception.userExeption.UserNotFoundException;
import com.example.automarket.exception.userExeption.UsernameAlreadyExistsException;
import com.example.automarket.exception.vehicleException.VehicleNotFoundException;
import com.example.automarket.exception.vehicleException.VehicleAccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
public class GlobalExceptionHandler {

    // ---------------- User Exceptions ----------------
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<APIResponse> handleUserNotFound(UserNotFoundException ex) {
        return new ResponseEntity<>(new APIResponse(ex.getMessage(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<APIResponse> handleUsernameExists(UsernameAlreadyExistsException ex) {
        return new ResponseEntity<>(new APIResponse(ex.getMessage(), HttpStatus.CONFLICT), HttpStatus.CONFLICT);
    }

    // ---------------- Vehicle Exceptions ----------------
    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<APIResponse> handleVehicleNotFound(VehicleNotFoundException ex) {
        return new ResponseEntity<>(new APIResponse(ex.getMessage(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VehicleAccessDeniedException.class)
    public ResponseEntity<APIResponse> handleVehicleAccessDenied(VehicleAccessDeniedException ex) {
        return new ResponseEntity<>(new APIResponse(ex.getMessage(), HttpStatus.FORBIDDEN), HttpStatus.FORBIDDEN);
    }

    // ---------------- Type Mismatch ----------------
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<APIResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "Invalid parameter: " + ex.getValue();
        return new ResponseEntity<>(new APIResponse(message, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    // ---------------- Access Denied ----------------
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<APIResponse> handleAccessDenied(org.springframework.security.access.AccessDeniedException ex) {
        return new ResponseEntity<>(new APIResponse(ex.getMessage(), HttpStatus.FORBIDDEN), HttpStatus.FORBIDDEN);
    }

    // ---------------- Generic Exception ----------------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse> handleGeneric(Exception ex) {
        return new ResponseEntity<>(new APIResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
