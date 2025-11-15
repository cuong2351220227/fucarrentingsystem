package com.example.fucarrentingsystem.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CarID")
    private Integer carID;

    @Column(name = "CarName", nullable = false)
    private String carName;

    @Column(name = "CarModelYear", nullable = false)
    private Integer carModelYear;

    @Column(name = "Color", nullable = false)
    private String color;

    @Column(name = "Capacity", nullable = false)
    private Integer capacity;

    @Column(name = "Description", nullable = false, length = 1000)
    private String description;

    @Column(name = "ImportDate", nullable = false)
    private LocalDate importDate;

    @ManyToOne
    @JoinColumn(name = "ProducerID", nullable = false)
    private CarProducer producer;

    @Column(name = "RentPrice", nullable = false)
    private Double rentPrice;

    @Column(name = "Status", nullable = false)
    private String status;

    // Constructors
    public Car() {}

    public Car(String carName, Integer carModelYear, String color, Integer capacity,
               String description, LocalDate importDate, CarProducer producer,
               Double rentPrice, String status) {
        this.carName = carName;
        this.carModelYear = carModelYear;
        this.color = color;
        this.capacity = capacity;
        this.description = description;
        this.importDate = importDate;
        this.producer = producer;
        this.rentPrice = rentPrice;
        this.status = status;
    }

    // Getters and Setters
    public Integer getCarID() {
        return carID;
    }

    public void setCarID(Integer carID) {
        this.carID = carID;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public Integer getCarModelYear() {
        return carModelYear;
    }

    public void setCarModelYear(Integer carModelYear) {
        this.carModelYear = carModelYear;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getImportDate() {
        return importDate;
    }

    public void setImportDate(LocalDate importDate) {
        this.importDate = importDate;
    }

    public CarProducer getProducer() {
        return producer;
    }

    public void setProducer(CarProducer producer) {
        this.producer = producer;
    }

    public Double getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(Double rentPrice) {
        this.rentPrice = rentPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

