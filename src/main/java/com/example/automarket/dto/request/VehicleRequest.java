package com.example.automarket.dto.request;

import com.example.automarket.enums.FuelType;
import com.example.automarket.enums.TransmissionType;
import com.example.automarket.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleRequest {
    private VehicleType type;
    private String brand;
    private String model;
    private Integer manufactureYear;
    private String engine;
    private Integer kw;
    private Integer mileage;
    private FuelType fuelType;
    private TransmissionType transmissionType;
    private Boolean serviceHistory;
    private Double price;
}
