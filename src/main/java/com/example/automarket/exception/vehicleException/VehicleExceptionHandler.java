package com.example.automarket.exception.vehicleException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class VehicleExceptionHandler {
    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<VehicleException> handleVehicleNotFound(VehicleNotFoundException ex) {
        VehicleException vehicleException = new VehicleException(
                ex.getMessage(),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(vehicleException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<VehicleException> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        VehicleException vehicleException = new VehicleException(
                "Invalid vehicle id: " + ex.getValue(),
                HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(vehicleException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VehicleAccessDeniedException.class)
    public ResponseEntity<VehicleException> handleAccessDenied(VehicleAccessDeniedException ex) {
        VehicleException vehicleException = new VehicleException(
                ex.getMessage(),
                HttpStatus.FORBIDDEN
        );
        return new ResponseEntity<>(vehicleException, HttpStatus.FORBIDDEN);
    }

}
