package com.example.automarket.service.impl;

import com.example.automarket.entity.Vehicle;
import com.example.automarket.repository.VehicleRepository;
import com.example.automarket.service.VehicleService;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository repo;

    public VehicleServiceImpl(VehicleRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return repo.findAll();
    }

    @Override
    public Optional<Vehicle> getVehicleById(Long id) {
        return repo.findById(id);
    }

    @Override
    public HttpStatus addVehicle(Vehicle vehicle) {
        repo.save(vehicle);
        return HttpStatus.CREATED;
    }

    @Override
    public HttpStatus editVehicle(Long id, Vehicle vehicle) {
        Vehicle exisingVehicle = repo.findById(id).orElseThrow();

        exisingVehicle.setType(vehicle.getType());
        exisingVehicle.setBrand(vehicle.getBrand());
        exisingVehicle.setModel(vehicle.getModel());
        exisingVehicle.setYear(vehicle.getYear());
        exisingVehicle.setTransmission(vehicle.getTransmission());
        exisingVehicle.setServiceHistory(vehicle.getServiceHistory());
        exisingVehicle.setPrice(vehicle.getPrice());

        repo.save(exisingVehicle);
        return HttpStatus.OK;
    }

    @Override
    public HttpStatus deleteVehicle(Long id) {
        repo.deleteById(id);
        return HttpStatus.OK;
    }
}
