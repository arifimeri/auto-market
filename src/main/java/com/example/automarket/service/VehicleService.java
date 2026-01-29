package com.example.automarket.service;

import com.example.automarket.model.User;
import com.example.automarket.model.Vehicle;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface VehicleService {

    List<Vehicle> getAllVehicles();

    List<Vehicle> getVehiclesByUser(User user);

    Vehicle getVehicleById(Long id);

    Vehicle saveVehicle(Vehicle vehicle);

    Boolean existsByBrandAndModelAndEngineAndYear(String brand, String model, String engine, Integer year);

    Vehicle editVehicle(Long id, Vehicle vehicle, Authentication authentication);

    void  deleteVehicle(Long id, Authentication authentication);

}
