package com.example.automarket.dto.request;

import com.example.automarket.enums.FuelType;
import com.example.automarket.enums.TransmissionType;
import com.example.automarket.enums.VehicleType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
public class VehicleRequest {

    @NotNull
    private VehicleType type;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotNull
    @Min(1900)
    @Max(2026)
    private Integer manufactureYear;

    @NotBlank
    private String engine;

    @NotNull
    @Min(1)
    private Integer kw;

    @NotNull
    @Min(0)
    private Integer mileage;

    @NotNull
    private FuelType fuelType;

    @NotNull
    private TransmissionType transmissionType;

    private Boolean serviceHistory;

    @NotNull
    @Positive
    @DecimalMin("0.0")
    private BigDecimal price;
}
