package com.example.automarket.config;

import com.example.automarket.enums.FuelType;
import com.example.automarket.enums.TransmissionType;
import com.example.automarket.enums.VehicleType;
import com.example.automarket.enums.Role;
import com.example.automarket.model.User;
import com.example.automarket.model.Vehicle;
import com.example.automarket.repository.VehicleRepository;
import com.example.automarket.service.UserService;
import com.example.automarket.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VehicleRepository vehicleRepository;


    @Override
    public void run(String... args) throws Exception {

        User admin = createUserIfNotExists("arif", "admin", Role.ROLE_ADMIN);
        User firstUser = createUserIfNotExists("filan", "fisteku", Role.ROLE_USER);
        User secondUser = createUserIfNotExists("artur", "11112222", Role.ROLE_USER);
        User visitor = createUserIfNotExists("aulon", "12345", Role.ROLE_VISITOR);

        createVehicleIfNotExists(admin, "Mercedes", "E220 d", 2020, "2.0 CDI", 140, 130000, FuelType.DIESEL, TransmissionType.AUTOMATIC, true, 25000.00);
        createVehicleIfNotExists(firstUser, "BMW", "550d", 2018, "2.0 TDI", 131, 180000, FuelType.PETROL, TransmissionType.MANUAL, true, 16500.00);
        createVehicleIfNotExists(secondUser, "Audi", "A8", 2023, "3.0 TDI", 190, 150000, FuelType.HYBRID, TransmissionType.AUTOMATIC, true, 32500.00);
        createVehicleIfNotExists(secondUser, "Tesla", "Model S", 2023, "Electric Engine", 492, 50000, FuelType.ELECTRIC, TransmissionType.AUTOMATIC, true, 94500.00);
    }

    private User createUserIfNotExists(String username, String password, Role role) {
        if (!userService.existsByUsername(username)) {
            return userService.createUser(
                    username,
                    passwordEncoder.encode(password),
                    role
            );
        }
        return userService.findByUsername(username);
    }


    private void createVehicleIfNotExists(User user,
                                          String brand,
                                          String model,
                                          Integer year,
                                          String engine,
                                          Integer kw,
                                          Integer mileage,
                                          FuelType fuelType,
                                          TransmissionType transmission,
                                          Boolean serviceHistory,
                                          Double price) {

        boolean exists = vehicleRepository
                .existsByBrandAndModelAndEngineAndManufactureYear(
                        brand, model, engine, year
                );

        if (!exists) {
            Vehicle vehicle = new Vehicle();
            vehicle.setUser(user);
            vehicle.setBrand(brand);
            vehicle.setModel(model);
            vehicle.setManufactureYear(year);
            vehicle.setEngine(engine);
            vehicle.setKw(kw);
            vehicle.setMileage(mileage);
            vehicle.setFuelType(fuelType);
            vehicle.setTransmissionType(transmission);
            vehicle.setServiceHistory(serviceHistory);
            vehicle.setPrice(BigDecimal.valueOf(price));
            vehicle.setType(VehicleType.CAR);

            vehicleRepository.save(vehicle);
        }
    }
}
