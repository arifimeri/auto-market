package com.example.automarket.service.impl;

import com.example.automarket.exception.vehicleException.VehicleNotFoundException;
import com.example.automarket.model.User;
import com.example.automarket.model.Vehicle;
import com.example.automarket.repository.VehicleRepository;
import com.example.automarket.service.VehicleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository repo;

    @Override
    public List<Vehicle> getAllVehicles() {
        return repo.findAll();
    }

    @Override
    public List<Vehicle> getVehiclesByUser(User user) {
        return repo.findByUser(user);
    }

    @Override
    public Vehicle getVehicleById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle with id " + id + " not found!"));
    }

    @Override
    public Vehicle saveVehicle(Vehicle vehicle) {
        return repo.save(vehicle);
    }

    @Transactional
    @Override
    public Vehicle editVehicle(Long id, Vehicle vehicle, Authentication auth) {
        Vehicle existing = getVehicleById(id);

        checkOwnership(existing, auth);

        existing.setType(vehicle.getType());
        existing.setBrand(vehicle.getBrand());
        existing.setModel(vehicle.getModel());
        existing.setManufactureYear(vehicle.getManufactureYear());
        existing.setTransmissionType(vehicle.getTransmissionType());
        existing.setFuelType(vehicle.getFuelType());
        existing.setServiceHistory(vehicle.getServiceHistory());
        existing.setPrice(vehicle.getPrice());

        return repo.save(existing);
    }

    @Override
    public void deleteVehicle(Long id, Authentication auth) {
        Vehicle existing = getVehicleById(id);

        checkOwnership(existing, auth);

        repo.delete(existing);
    }

    private void checkOwnership(Vehicle vehicle, Authentication auth) {

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) return;

        if (!vehicle.getUser().getUsername().equals(auth.getName())) {
            throw new AccessDeniedException("You have no right to do this!");
        }
    }
}
