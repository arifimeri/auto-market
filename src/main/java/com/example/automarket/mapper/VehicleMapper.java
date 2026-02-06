package com.example.automarket.mapper;

import com.example.automarket.dto.request.VehicleRequest;
import com.example.automarket.dto.response.VehicleResponse;
import com.example.automarket.model.Vehicle;

public class VehicleMapper {

    public static Vehicle toEntity(VehicleRequest dto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setType(dto.getType());
        vehicle.setBrand(dto.getBrand());
        vehicle.setModel(dto.getModel());
        vehicle.setManufactureYear(dto.getManufactureYear());
        vehicle.setEngine(dto.getEngine());
        vehicle.setKw(dto.getKw());
        vehicle.setMileage(dto.getMileage());
        vehicle.setFuelType(dto.getFuelType());
        vehicle.setTransmissionType(dto.getTransmissionType());
        vehicle.setServiceHistory(dto.getServiceHistory());
        vehicle.setPrice(dto.getPrice());
        return vehicle;
    }


    public static void updateEntity(Vehicle v, VehicleRequest dto) {
        v.setType(dto.getType());
        v.setBrand(dto.getBrand());
        v.setModel(dto.getModel());
        v.setManufactureYear(dto.getManufactureYear());
        v.setEngine(dto.getEngine());
        v.setKw(dto.getKw());
        v.setMileage(dto.getMileage());
        v.setFuelType(dto.getFuelType());
        v.setTransmissionType(dto.getTransmissionType());
        v.setServiceHistory(dto.getServiceHistory());
        v.setPrice(dto.getPrice());
    }

    public static  VehicleResponse toResponse(Vehicle vehicle) {
        VehicleResponse dto = new VehicleResponse();
        dto.setId(vehicle.getId());
        dto.setType(vehicle.getType());
        dto.setBrand(vehicle.getBrand());
        dto.setModel(vehicle.getModel());
        dto.setManufactureYear(vehicle.getManufactureYear());
        dto.setEngine(vehicle.getEngine());
        dto.setKw(vehicle.getKw());
        dto.setMileage(vehicle.getMileage());
        dto.setFuelType(vehicle.getFuelType());
        dto.setTransmissionType(vehicle.getTransmissionType());
        dto.setServiceHistory(vehicle.getServiceHistory());
        dto.setPrice(vehicle.getPrice());
        dto.setOwnerUsername(vehicle.getUser().getUsername());
        return dto;
    }
}
