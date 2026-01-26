package com.example.automarket.config;

import com.example.automarket.enums.FuelType;
import com.example.automarket.enums.TransmissionType;
import com.example.automarket.model.Role;
import com.example.automarket.model.User;
import com.example.automarket.model.Vehicle;
import com.example.automarket.service.UserService;
import com.example.automarket.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setBrand("Mercedes");
        vehicle1.setModel("E220 d");
        vehicle1.setManufactureYear(2020);
        vehicle1.setEngine("2.0 CDI");
        vehicle1.setKw(140);
        vehicle1.setMileage(130000);
        vehicle1.setFuelType(FuelType.DIESEL);
        vehicle1.setTransmissionType(TransmissionType.AUTOMATIC);
        vehicle1.setServiceHistory(true);
        vehicle1.setPrice(25000.00);
        vehicleService.saveVehicle(vehicle1);

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setBrand("BMW");
        vehicle2.setModel("550d");
        vehicle2.setManufactureYear(2018);
        vehicle2.setEngine("2.0 TDI");
        vehicle2.setKw(131);
        vehicle2.setMileage(180000);
        vehicle2.setFuelType(FuelType.PETROL);
        vehicle2.setTransmissionType(TransmissionType.MANUAL);
        vehicle2.setServiceHistory(true);
        vehicle2.setPrice(16500.00);
        vehicleService.saveVehicle(vehicle2);

        Vehicle vehicle3 = new Vehicle();
        vehicle2.setBrand("Audi");
        vehicle2.setModel("A8");
        vehicle2.setManufactureYear(2023);
        vehicle2.setEngine("3.0 TDI");
        vehicle2.setKw(190);
        vehicle2.setMileage(150000);
        vehicle2.setFuelType(FuelType.HYBRID);
        vehicle2.setTransmissionType(TransmissionType.AUTOMATIC);
        vehicle2.setServiceHistory(true);
        vehicle2.setPrice(32500.00);
        vehicleService.saveVehicle(vehicle3);

        Vehicle vehicle4 = new Vehicle();
        vehicle2.setBrand("Tesla");
        vehicle2.setModel("Model S");
        vehicle2.setManufactureYear(2023);
        vehicle2.setEngine("Electric Engine");
        vehicle2.setKw(492);
        vehicle2.setMileage(50000);
        vehicle2.setFuelType(FuelType.ELECTRIC);
        vehicle2.setTransmissionType(TransmissionType.AUTOMATIC);
        vehicle2.setServiceHistory(true);
        vehicle2.setPrice(94500.00);
        vehicleService.saveVehicle(vehicle4);

        if (!userService.existsByUsername("admin"))
        {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("secret"));
            admin.setRole(Role.ADMIN);
            userService.saveUser(admin);
        }

        if (!userService.existsByUsername("user")) {
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("secret"));
            user.setRole(Role.USER);
            userService.saveUser(user);
        }

        if (!userService.existsByUsername("visitor")) {
            User visitor = new User();
            visitor.setUsername("visitor");
            visitor.setPassword(passwordEncoder.encode("secret"));
            visitor.setRole(Role.VISITOR);
            userService.saveUser(visitor);
        }

    }
}
