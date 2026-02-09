package com.example.automarket.controller;

import com.example.automarket.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicles/{vehicleId}/images")
public class VehicleImageController {

    private final VehicleService vehicleService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Void> upload(
            @PathVariable Long vehicleId,
            @RequestParam MultipartFile file,
            Authentication auth
    ) {
        vehicleService.addImage(vehicleId, file, auth.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{imageId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long vehicleId,
            @PathVariable Long imageId,
            Authentication auth
    ) {
        vehicleService.deleteImage(vehicleId, imageId, auth.getName());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{imageId}/primary")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public void setPrimary(
            @PathVariable Long vehicleId,
            @PathVariable Long imageId,
            Authentication auth
    ) {
        vehicleService.setPrimaryImage(vehicleId, imageId, auth.getName());
    }

    @PutMapping("/reorder")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public void reorder(
            @PathVariable Long vehicleId,
            @RequestBody List<Long> orderedIds,
            Authentication auth
    ) {
        vehicleService.reorderImages(vehicleId, orderedIds, auth.getName());
    }
}
