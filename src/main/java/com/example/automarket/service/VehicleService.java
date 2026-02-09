package com.example.automarket.service;

import com.example.automarket.dto.request.VehicleRequest;
import com.example.automarket.model.User;
import com.example.automarket.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VehicleService {

    Page<Vehicle> getAllVehicles(Pageable pageable);

    Page<Vehicle> getVehiclesByUser(String username, Pageable pageable);

    Vehicle getVehicleById(Long id);

    Vehicle createVehicle(VehicleRequest vehicleRequest, String username);

    Vehicle updateVehicle(Long id, VehicleRequest vehicleRequest, String username);

    void deleteVehicle(Long id, String username);

    Page<Vehicle> searchVehicles(
            String brand,
            String model,
            Integer priceMin,
            Integer priceMax,
            Integer yearMin,
            Integer yearMax,
            String fuelType,
            String transmission,
            Pageable pageable
    );

    void addImage(Long vehicleId, MultipartFile file, String username);

    void setPrimaryImage(Long vehicleId, Long imageId, String username);

    void reorderImages(Long vehicleId, List<Long> orderedIds, String username);

    void deleteImage(Long vehicleId, Long imageId, String username);
}
