package com.example.automarket.controller;

import com.example.automarket.model.User;
import com.example.automarket.model.Vehicle;
import com.example.automarket.response.APIResponse;
import com.example.automarket.service.UserService;
import com.example.automarket.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final UserService userService;

    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/{id}")
    public Vehicle getVehicleById(@PathVariable Long id) {
        return vehicleService.getVehicleById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<APIResponse> addVehicle(@RequestBody Vehicle vehicle,
                                                  Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        vehicle.setUser(user);
        Vehicle savedVehicle = vehicleService.saveVehicle(vehicle);
        String message = "Vehicle with id " + savedVehicle.getId() + " was created successfully.";
        return ResponseEntity.ok(new APIResponse(message, HttpStatus.OK));
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<APIResponse> editVehicle(@PathVariable Long id,
                                                       @RequestBody Vehicle vehicle,
                                                       Authentication authentication) {
        Vehicle updatedVehicle = vehicleService.editVehicle(id, vehicle, authentication);
        String message = "Vehicle with id " + updatedVehicle.getId() + " was updated successfully.";
        return ResponseEntity.ok(new APIResponse(message, HttpStatus.OK));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<APIResponse> deleteVehicle(@PathVariable Long id,
                                                        Authentication authentication) {
        vehicleService.deleteVehicle(id, authentication);
        String message = "Vehicle with id " + id + " was deleted successfully.";
        return ResponseEntity.ok(new APIResponse(message, HttpStatus.OK));
    }

    @GetMapping("/myVehicles")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Vehicle> getMyVehicles(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        return vehicleService.getVehiclesByUser(user);
    }
}
