package com.example.automarket.config;

import com.example.automarket.enums.FuelType;
import com.example.automarket.enums.TransmissionType;
import com.example.automarket.enums.VehicleType;
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

        // --- USERS ---
        if (!userService.existsByUsername("arif")) {
            User admin = new User();
            admin.setUsername("arif");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole(Role.ROLE_ADMIN);
            userService.saveUser(admin);
        }

        if (!userService.existsByUsername("filan")) {
            User firstUser = new User();
            firstUser.setUsername("filan");
            firstUser.setPassword(passwordEncoder.encode("fisteku"));
            firstUser.setRole(Role.ROLE_USER);
            userService.saveUser(firstUser);
        }

        if (!userService.existsByUsername("aulon")) {
            User visitor = new User();
            visitor.setUsername("aulon");
            visitor.setPassword(passwordEncoder.encode("12345"));
            visitor.setRole(Role.ROLE_VISITOR);
            userService.saveUser(visitor);
        }

        if (!userService.existsByUsername("artur")) {
            User secondUser = new User();
            secondUser.setUsername("artur");
            secondUser.setPassword(passwordEncoder.encode("11112222"));
            secondUser.setRole(Role.ROLE_USER);
            userService.saveUser(secondUser);
        }

        User admin = userService.findByUsername("arif");
        User firstUser = userService.findByUsername("filan");
        User secondUser = userService.findByUsername("artur");

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setType(VehicleType.CAR);
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
        vehicle1.setUser(admin);
        vehicleService.saveVehicle(vehicle1);

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setType(VehicleType.CAR);
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
        vehicle2.setUser(firstUser);
        vehicleService.saveVehicle(vehicle2);

        Vehicle vehicle3 = new Vehicle();
        vehicle3.setType(VehicleType.CAR);
        vehicle3.setBrand("Audi");
        vehicle3.setModel("A8");
        vehicle3.setManufactureYear(2023);
        vehicle3.setEngine("3.0 TDI");
        vehicle3.setKw(190);
        vehicle3.setMileage(150000);
        vehicle3.setFuelType(FuelType.HYBRID);
        vehicle3.setTransmissionType(TransmissionType.AUTOMATIC);
        vehicle3.setServiceHistory(true);
        vehicle3.setPrice(32500.00);
        vehicle3.setUser(secondUser);
        vehicleService.saveVehicle(vehicle3);

        Vehicle vehicle4 = new Vehicle();
        vehicle4.setType(VehicleType.CAR);
        vehicle4.setBrand("Tesla");
        vehicle4.setModel("Model S");
        vehicle4.setManufactureYear(2023);
        vehicle4.setEngine("Electric Engine");
        vehicle4.setKw(492);
        vehicle4.setMileage(50000);
        vehicle4.setFuelType(FuelType.ELECTRIC);
        vehicle4.setTransmissionType(TransmissionType.AUTOMATIC);
        vehicle4.setServiceHistory(true);
        vehicle4.setPrice(94500.00);
        vehicle4.setUser(secondUser);
        vehicleService.saveVehicle(vehicle4);
    }
}
