package com.example.automarket.mapper;

import com.example.automarket.dto.request.VehicleRequest;
import com.example.automarket.dto.response.VehicleResponse;
import com.example.automarket.model.User;
import com.example.automarket.model.Vehicle;
import com.example.automarket.model.VehicleImage;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class VehicleMapper {

    public Vehicle toEntity(VehicleRequest request, User user) {
        Vehicle v = new Vehicle();
        v.setUser(user);
        v.setType(request.getType());
        v.setBrand(request.getBrand());
        v.setModel(request.getModel());
        v.setEngine(request.getEngine());
        v.setManufactureYear(request.getManufactureYear());
        v.setKw(request.getKw());
        v.setMileage(request.getMileage());
        v.setFuelType(request.getFuelType());
        v.setTransmissionType(request.getTransmissionType());
        v.setServiceHistory(request.getServiceHistory());
        v.setPrice(request.getPrice());
        return v;
    }

    public VehicleResponse toResponse(Vehicle vehicle) {
        VehicleResponse resp = new VehicleResponse();

        resp.setId(vehicle.getId());
        resp.setType(vehicle.getType());
        resp.setBrand(vehicle.getBrand());
        resp.setModel(vehicle.getModel());
        resp.setManufactureYear(vehicle.getManufactureYear());
        resp.setEngine(vehicle.getEngine());
        resp.setKw(vehicle.getKw());
        resp.setMileage(vehicle.getMileage());
        resp.setFuelType(vehicle.getFuelType());
        resp.setTransmissionType(vehicle.getTransmissionType());
        resp.setServiceHistory(vehicle.getServiceHistory());
        resp.setPrice(vehicle.getPrice());

        resp.setOwnerUsername(
                vehicle.getUser() != null ? vehicle.getUser().getUsername() : null
        );

        if (vehicle.getImages() != null && !vehicle.getImages().isEmpty()) {

            List<VehicleImage> sortedImages = vehicle.getImages().stream()
                    .sorted(Comparator.comparingInt(VehicleImage::getPosition))
                    .toList();

            resp.setImages(
                    sortedImages.stream()
                            .map(VehicleImage::getImageUrl)
                            .toList()
            );

            resp.setPrimaryImage(
                    sortedImages.stream()
                            .filter(VehicleImage::isPrimaryImage)
                            .findFirst()
                            .map(VehicleImage::getImageUrl)
                            .orElse(sortedImages.get(0).getImageUrl())
            );
        }

        return resp;
    }
}
