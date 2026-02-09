package com.example.automarket.repository;

import com.example.automarket.model.User;
import com.example.automarket.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {
    List<Vehicle> findByUser(User user);
    Boolean existsByBrandAndModelAndEngineAndManufactureYear(String brand,
                                                             String model,
                                                             String engine,
                                                             Integer manufactureYear);

    Page<Vehicle> findAll(Pageable pageable);
    Page<Vehicle> findByUser(User user, Pageable pageable);

}
