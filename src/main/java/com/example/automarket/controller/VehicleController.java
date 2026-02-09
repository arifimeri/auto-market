package com.example.automarket.controller;

import com.example.automarket.dto.request.VehicleRequest;
import com.example.automarket.dto.response.VehicleResponse;
import com.example.automarket.mapper.VehicleMapper;
import com.example.automarket.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final VehicleMapper vehicleMapper;

    // ======================
    // PUBLIC – SEARCH + LIST
    // ======================

    @GetMapping
    public Page<VehicleResponse> searchVehicles(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer priceMin,
            @RequestParam(required = false) Integer priceMax,
            @RequestParam(required = false) Integer yearMin,
            @RequestParam(required = false) Integer yearMax,
            @RequestParam(required = false) String fuelType,
            @RequestParam(required = false) String transmission,
            Pageable pageable
    ) {
        return vehicleService
                .searchVehicles(brand, model, priceMin, priceMax, yearMin, yearMax, fuelType, transmission, pageable)
                .map(vehicleMapper::toResponse);
    }

    @GetMapping("/{id}")
    public VehicleResponse getById(@PathVariable Long id) {
        return vehicleMapper.toResponse(
                vehicleService.getVehicleById(id)
        );
    }

    // ======================
    // AUTHENTICATED
    // ======================

    @GetMapping("/my")
    public Page<VehicleResponse> myVehicles(
            Authentication auth,
            Pageable pageable
    ) {
        return vehicleService
                .getVehiclesByUser(auth.getName(), pageable)
                .map(vehicleMapper::toResponse);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<VehicleResponse> create(
            @Valid @RequestBody VehicleRequest request,
            Authentication auth
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vehicleMapper.toResponse(
                        vehicleService.createVehicle(request, auth.getName())
                ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public VehicleResponse update(
            @PathVariable Long id,
            @Valid @RequestBody VehicleRequest request,
            Authentication auth
    ) {
        return vehicleMapper.toResponse(
                vehicleService.updateVehicle(id, request, auth.getName())
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            Authentication auth
    ) {
        vehicleService.deleteVehicle(id, auth.getName());
        return ResponseEntity.noContent().build();
    }
}
