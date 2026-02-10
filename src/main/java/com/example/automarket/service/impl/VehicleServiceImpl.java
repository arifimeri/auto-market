package com.example.automarket.service.impl;

import com.example.automarket.dto.request.VehicleRequest;
import com.example.automarket.exception.vehicleException.VehicleAccessDeniedException;
import com.example.automarket.exception.vehicleException.VehicleNotFoundException;
import com.example.automarket.model.User;
import com.example.automarket.model.Vehicle;
import com.example.automarket.model.VehicleImage;
import com.example.automarket.repository.VehicleImageRepository;
import com.example.automarket.repository.VehicleRepository;
import com.example.automarket.service.UserService;
import com.example.automarket.service.VehicleService;
import com.example.automarket.specification.VehicleSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.automarket.service.FileStorageService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository repo;
    private final UserService userService;

    private final FileStorageService fileStorageService;
    private final VehicleImageRepository imageRepo;


    @Override
    public Page<Vehicle> getAllVehicles(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public Page<Vehicle> getVehiclesByUser(String username, Pageable pageable) {
        User user = userService.findByUsername(username);
        return repo.findByUser(user, pageable);
    }

    @Override
    public Vehicle getVehicleById(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new VehicleNotFoundException(
                                "Vehicle with id " + id + " not found"
                        )
                );
    }

    @Override
    @Transactional
    public Vehicle createVehicle(VehicleRequest request, String username) {
        User user = userService.findByUsername(username);

        Vehicle vehicle = new Vehicle();
        vehicle.setUser(user);
        applyRequestToVehicle(vehicle, request);

        return repo.save(vehicle);
    }

    @Override
    @Transactional
    public Vehicle updateVehicle(Long id, VehicleRequest request, String username) {
        Vehicle existing = getVehicleById(id);

        authorize(existing, username);

        applyRequestToVehicle(existing, request);

        return repo.save(existing);
    }

    @Override
    @Transactional
    public void deleteVehicle(Long id, String username) {
        Vehicle existing = getVehicleById(id);

        authorize(existing, username);

        repo.delete(existing);
    }

    @Override
    public Page<Vehicle> searchVehicles(String brand, String model, Integer priceMin, Integer priceMax,
                                        Integer yearMin, Integer yearMax, String fuelType, String transmission,
                                        Pageable pageable) {
        Specification<Vehicle> spec =
                VehicleSpecification.hasBrand(brand)
                        .and(VehicleSpecification.hasModel(model))
                        .and(VehicleSpecification.priceBetween(priceMin, priceMax))
                        .and(VehicleSpecification.yearBetween(yearMin, yearMax))
                        .and(VehicleSpecification.fuelType(fuelType))
                        .and(VehicleSpecification.transmission(transmission));

        return repo.findAll(spec, pageable);
    }

    // =========================
    // PRIVATE HELPERS
    // =========================

    private void authorize(Vehicle vehicle, String username) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!vehicle.getUser().getUsername().equals(username) && !isAdmin) {
            throw new VehicleAccessDeniedException(
                    "You do not have permission to modify this vehicle"
            );
        }
    }

    private void applyRequestToVehicle(Vehicle vehicle, VehicleRequest request) {
        vehicle.setType(request.getType());
        vehicle.setBrand(request.getBrand());
        vehicle.setModel(request.getModel());
        vehicle.setManufactureYear(request.getManufactureYear());
        vehicle.setEngine(request.getEngine());
        vehicle.setKw(request.getKw());
        vehicle.setMileage(request.getMileage());
        vehicle.setFuelType(request.getFuelType());
        vehicle.setTransmissionType(request.getTransmissionType());
        vehicle.setServiceHistory(request.getServiceHistory());
        vehicle.setPrice(request.getPrice());
    }

    @Transactional
    public void addImage(
            Long vehicleId,
            MultipartFile file,
            String username) {

        Vehicle vehicle = getVehicleById(vehicleId);

        if (!vehicle.getUser().getUsername().equals(username)) {
            throw new VehicleAccessDeniedException(
                    "You do not own this vehicle");
        }

        String url = fileStorageService.store(file, vehicleId);

        VehicleImage image = new VehicleImage();
        image.setImageUrl(url);
        image.setVehicle(vehicle);

        vehicle.getImages().add(image);
        repo.save(vehicle);
    }


    @Transactional
    public void setPrimaryImage(Long vehicleId, Long imageId, String username) {
        Vehicle vehicle = getVehicleById(vehicleId);
        authorize(vehicle, username);

        for (VehicleImage img : vehicle.getImages()) {
            img.setPrimaryImage(img.getId().equals(imageId));
        }

        repo.save(vehicle); // ruan ndryshimet
    }


    @Transactional
    public void reorderImages(Long vehicleId, List<Long> orderedIds, String username) {
        Vehicle vehicle = getVehicleById(vehicleId);
        authorize(vehicle, username);

        List<VehicleImage> images = vehicle.getImages();

        if (orderedIds.size() != images.size()) {
            throw new IllegalArgumentException("Mismatch between provided order and image count");
        }

        for (int i = 0; i < orderedIds.size(); i++) {
            Long id = orderedIds.get(i);
            VehicleImage img = images.stream()
                    .filter(image -> image.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Image not found"));
            img.setPosition(i);
        }

        repo.save(vehicle);
    }

    @Transactional
    public void deleteImage(Long vehicleId, Long imageId, String username) {
        Vehicle vehicle = getVehicleById(vehicleId);
        authorize(vehicle, username);

        VehicleImage image = vehicle.getImages().stream()
                .filter(img -> img.getId().equals(imageId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Image not found"));

        fileStorageService.delete(image.getImageUrl());

        vehicle.getImages().remove(image);

        if (image.isPrimaryImage() && !vehicle.getImages().isEmpty()) {
            vehicle.getImages().get(0).setPrimaryImage(true);
        }
        repo.save(vehicle);
    }
}
