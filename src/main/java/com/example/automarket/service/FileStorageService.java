package com.example.automarket.service;

import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String store(MultipartFile file, Long vehicleId) {
        try {
            Files.createDirectories(Paths.get(uploadDir));

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path targetPath = Paths.get(uploadDir).resolve(filename);

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            // kjo URL do të përdoret nga frontend
            return "/uploads/" + filename;

        } catch (IOException e) {
            throw new RuntimeException("Could not store file", e);
        }
    }

    public void delete(String imageUrl) {
        try {
            String filename = Paths.get(imageUrl).getFileName().toString();
            Path path = Paths.get(uploadDir).resolve(filename);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("Could not delete file: " + imageUrl, e);
        }
    }
}
