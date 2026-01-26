package com.example.automarket.service;

import com.example.automarket.model.User;
import com.example.automarket.model.Vehicle;

import java.util.List;

public interface VehicleService {

    List<Vehicle> getAllVehicles();

    List<Vehicle> getVehiclesByUser(User user);

    Vehicle getVehicleById(Long id);

    Vehicle saveVehicle(Vehicle vehicle);

    Vehicle editVehicle(Long id, Vehicle vehicle);

    void  deleteVehicle(Long id);
}
