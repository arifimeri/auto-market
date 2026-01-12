package com.example.automarket.controller;

import com.example.automarket.entity.Vehicle;
import com.example.automarket.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @GetMapping
    public List<Vehicle> getAllVehicles(){
      return service.getAllVehicles();
    }

    @GetMapping("/{id}")
    public Optional<Vehicle> getVehicleById(@PathVariable Long id) {
        return service.getVehicleById(id);
    }

    @PostMapping
    public HttpStatus addVehicle(@RequestBody Vehicle vehicle) {
        return service.addVehicle(vehicle);
    }

    @PutMapping("/{id}")
    public HttpStatus editVehicle(@RequestBody Vehicle vehicle, @PathVariable Long id) {
        return service.editVehicle(id, vehicle);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteVehicle(@PathVariable Long id) {
        return service.deleteVehicle(id);
    }
}
