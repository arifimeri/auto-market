package com.example.automarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class VehicleExceptionHandler {

    @ExceptionHandler(value = {VehicleNotFoundException.class})
    public ResponseEntity<Object> handleVehicleNotFoundException(VehicleNotFoundException vehicleNotFoundException){
        VehicleException vehicleException = new VehicleException(
            vehicleNotFoundException.getMessage(),
            vehicleNotFoundException.getCause(),
            HttpStatus.NOT_FOUND
            );

        return new ResponseEntity<>(vehicleException, HttpStatus.NOT_FOUND);
    }
}
