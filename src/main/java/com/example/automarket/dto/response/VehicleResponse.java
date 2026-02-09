package com.example.automarket.dto.response;

import com.example.automarket.enums.FuelType;
import com.example.automarket.enums.TransmissionType;
import com.example.automarket.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponse {

    private Long id;

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

    private BigDecimal price;

    private String ownerUsername;

    private String primaryImage;
    private List<String> images;
}
