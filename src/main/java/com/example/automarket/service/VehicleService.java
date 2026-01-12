package com.example.automarket.service;

import com.example.automarket.entity.Vehicle;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface VehicleService {

    List<Vehicle> getAllVehicles();

    Optional<Vehicle> getVehicleById(Long id);

    HttpStatus addVehicle(Vehicle vehicle);

    HttpStatus editVehicle(Long id, Vehicle vehicle);

    HttpStatus deleteVehicle(Long id);
}
