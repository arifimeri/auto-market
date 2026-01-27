package com.example.automarket.exception.userExeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserException> handleUserNotFound(UserNotFoundException ex) {
        UserException userException = new UserException(
                ex.getMessage(),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(userException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<UserException> handleTypeMisMatch(MethodArgumentTypeMismatchException ex) {
        UserException userException = new UserException(
                "Invalid user id: " + ex.getValue(),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(userException, HttpStatus.BAD_REQUEST);
    }
}
