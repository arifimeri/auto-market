package com.example.automarket.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String Brand;
    private String model;
    private Integer year;
    private String transmission;
    private Boolean serviceHistory;
    private Double price;

}
