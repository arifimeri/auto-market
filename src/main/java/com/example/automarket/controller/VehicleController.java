package com.example.automarket.controller;

import com.example.automarket.model.User;
import com.example.automarket.model.Vehicle;
import com.example.automarket.service.UserService;
import com.example.automarket.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final UserService userService;

    @GetMapping
    public List<Vehicle> getAllVehicles(){
      return vehicleService.getAllVehicles();
    }

    @GetMapping("/{id}")
    public Vehicle getVehicleById(@PathVariable Long id) {
        return vehicleService.getVehicleById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Vehicle addVehicle(@RequestBody Vehicle vehicle, Authentication authentication) {
        if (authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority()
                .equals("ROLE_USER"))) {

            User user = userService.findByUsername(authentication.getName());
            vehicle.setUser(user);
        }
        return vehicleService.saveVehicle(vehicle);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Vehicle editVehicle(@RequestBody Vehicle vehicle, @PathVariable Long id, Authentication authentication) {
        Vehicle existing = vehicleService.getVehicleById(id);

        if (authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals("ROLE_USER"))) {
            if (!existing.getUser().getUsername().equals(authentication.getName())) {
                throw new RuntimeException("Not authorized");
            }
        }

        return vehicleService.editVehicle(id, vehicle);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public void deleteVehicle(@PathVariable Long id, Authentication authentication) {
        Vehicle vehicleExisting = vehicleService.getVehicleById(id);

        if (authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals("ROLE_USER"))) {
            throw new RuntimeException("Not authorized!");
        }
       vehicleService.deleteVehicle(id);
    }

    @GetMapping("/myVehicles")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Vehicle> getMyVehicles(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        return vehicleService.getVehiclesByUser(user);
    }
}
