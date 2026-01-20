package com.example.automarket.service;

import com.example.automarket.model.Vehicle;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;

public interface VehicleService {

    List<Vehicle> getAllVehicles();

    Vehicle getVehicleById(Long id);

    Vehicle addVehicle(Vehicle vehicle);

    Vehicle editVehicle(Long id, Vehicle vehicle);

    void  deleteVehicle(Long id);
}
