package com.example.automarket.service.impl;

import com.example.automarket.model.User;
import com.example.automarket.model.Vehicle;
import com.example.automarket.repository.VehicleRepository;
import com.example.automarket.service.VehicleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
    public List<Vehicle> getVehiclesByUser(User user) {
        return repo.findByUser(user);
    }

    @Override
    public Vehicle getVehicleById(Long id){
        return repo.findById(id).orElse(null);
    };

    @Override
    public Vehicle saveVehicle(Vehicle vehicle) {
      return repo.save(vehicle);
    }

    @Transactional
    @Override
    public Vehicle editVehicle(Long id, Vehicle vehicle) {
        Vehicle exisingVehicle = repo.findById(id).orElseThrow();

        exisingVehicle.setType(vehicle.getType());
        exisingVehicle.setBrand(vehicle.getBrand());
        exisingVehicle.setModel(vehicle.getModel());
        exisingVehicle.setManufactureYear(vehicle.getManufactureYear());
        exisingVehicle.setTransmissionType(vehicle.getTransmissionType());
        exisingVehicle.setFuelType(vehicle.getFuelType());
        exisingVehicle.setServiceHistory(vehicle.getServiceHistory());
        exisingVehicle.setPrice(vehicle.getPrice());

        return  repo.save(exisingVehicle);
    }

    @Override
    public void deleteVehicle(Long id) {
        repo.deleteById(id);
    }
}
