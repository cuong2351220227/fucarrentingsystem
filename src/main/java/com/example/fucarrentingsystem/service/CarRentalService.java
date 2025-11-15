package com.example.fucarrentingsystem.service;

import com.example.fucarrentingsystem.entity.CarRental;
import com.example.fucarrentingsystem.repository.CarRentalRepository;

import java.time.LocalDate;
import java.util.List;

public class CarRentalService {
    private final CarRentalRepository carRentalRepository;

    public CarRentalService() {
        this.carRentalRepository = new CarRentalRepository();
    }

    public List<CarRental> findAll() {
        return carRentalRepository.findAll();
    }

    public List<CarRental> findByCustomerId(Integer customerId) {
        return carRentalRepository.findByCustomerId(customerId);
    }

    public List<CarRental> findByCarId(Integer carId) {
        return carRentalRepository.findByCarId(carId);
    }

    /**
     * Generate rental report for a date range
     * Results are sorted in descending order by rent price
     */
    public List<CarRental> generateRentalReport(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        return carRentalRepository.findByDateRange(startDate, endDate);
    }

    public CarRental save(CarRental carRental) {
        // Validate pickup date is before return date
        if (carRental.getPickupDate().isAfter(carRental.getReturnDate()) ||
            carRental.getPickupDate().isEqual(carRental.getReturnDate())) {
            throw new IllegalArgumentException("Pickup date must be before return date");
        }
        return carRentalRepository.save(carRental);
    }

    public CarRental update(CarRental carRental) {
        // Validate pickup date is before return date
        if (carRental.getPickupDate().isAfter(carRental.getReturnDate()) ||
            carRental.getPickupDate().isEqual(carRental.getReturnDate())) {
            throw new IllegalArgumentException("Pickup date must be before return date");
        }
        return carRentalRepository.update(carRental);
    }
}

