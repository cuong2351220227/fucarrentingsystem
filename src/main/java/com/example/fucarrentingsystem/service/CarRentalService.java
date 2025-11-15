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
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
        return carRentalRepository.findByCustomerId(customerId);
    }

    public List<CarRental> findByCarId(Integer carId) {
        if (carId == null) {
            throw new IllegalArgumentException("Car ID cannot be null");
        }
        return carRentalRepository.findByCarId(carId);
    }

    /**
     * Check if there's a rental conflict for the given car and date range
     */
    public boolean hasRentalConflict(Integer carId, LocalDate pickupDate, LocalDate returnDate) {
        if (carId == null || pickupDate == null || returnDate == null) {
            throw new IllegalArgumentException("Car ID, pickup date, and return date cannot be null");
        }

        List<CarRental> existingRentals = carRentalRepository.findByCarId(carId);

        return existingRentals.stream().anyMatch(rental ->
            "Active".equals(rental.getStatus()) &&
            dateRangeOverlaps(pickupDate, returnDate, rental.getPickupDate(), rental.getReturnDate())
        );
    }

    /**
     * Check if customer already has an active rental for the same car and pickup date
     */
    public boolean hasCustomerCarConflict(Integer customerId, Integer carId, LocalDate pickupDate) {
        if (customerId == null || carId == null || pickupDate == null) {
            return false;
        }

        List<CarRental> customerRentals = findByCustomerId(customerId);

        return customerRentals.stream().anyMatch(rental ->
            rental.getCar().getCarID().equals(carId) &&
            rental.getPickupDate().equals(pickupDate) &&
            "Active".equals(rental.getStatus())
        );
    }

    /**
     * Check if two date ranges overlap
     */
    private boolean dateRangeOverlaps(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return !start1.isAfter(end2) && !end1.isBefore(start2);
    }

    /**
     * Generate rental report for a date range
     * Results are sorted in descending order by rent price
     */
    public List<CarRental> generateRentalReport(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }
        return carRentalRepository.findByDateRange(startDate, endDate);
    }

    public CarRental save(CarRental carRental) {
        System.out.println("=== CarRentalService.save() Debug ===");

        try {
            System.out.println("1. Starting validation...");
            validateCarRental(carRental);
            System.out.println("✓ Basic validation passed");

            // Additional detailed validation
            System.out.println("2. Detailed validation...");
            if (carRental.getCustomer() == null) {
                throw new IllegalArgumentException("Customer object is null");
            }
            if (carRental.getCar() == null) {
                throw new IllegalArgumentException("Car object is null");
            }

            Integer customerId = carRental.getCustomer().getCustomerID();
            Integer carId = carRental.getCar().getCarID();

            System.out.println("   - Customer ID: " + customerId);
            System.out.println("   - Car ID: " + carId);
            System.out.println("   - Pickup Date: " + carRental.getPickupDate());
            System.out.println("   - Return Date: " + carRental.getReturnDate());
            System.out.println("   - Rent Price: " + carRental.getRentPrice());
            System.out.println("   - Status: " + carRental.getStatus());

            if (customerId == null) {
                throw new IllegalArgumentException("Customer ID is null");
            }
            if (carId == null) {
                throw new IllegalArgumentException("Car ID is null");
            }
            System.out.println("✓ Detailed validation passed");

            // Check for conflicts
            System.out.println("3. Checking rental conflicts...");
            boolean hasRentalConflict = hasRentalConflict(carId, carRental.getPickupDate(), carRental.getReturnDate());
            if (hasRentalConflict) {
                System.out.println("❌ Rental conflict detected");
                throw new IllegalStateException("Car is already rented during this period");
            }
            System.out.println("✓ No rental conflicts");

            System.out.println("4. Checking customer-car conflicts...");
            boolean hasCustomerConflict = hasCustomerCarConflict(customerId, carId, carRental.getPickupDate());
            if (hasCustomerConflict) {
                System.out.println("❌ Customer-car conflict detected");
                throw new IllegalStateException("Customer already has this car rented on the same pickup date");
            }
            System.out.println("✓ No customer-car conflicts");

            System.out.println("5. Saving to repository...");
            CarRental saved = carRentalRepository.save(carRental);

            if (saved == null) {
                throw new RuntimeException("Repository returned null after save operation");
            }

            System.out.println("✓ Successfully saved with ID: " + saved.getRentalID());

            return saved;

        } catch (IllegalArgumentException e) {
            System.err.println("❌ Validation error in CarRentalService.save(): " + e.getMessage());
            throw e;
        } catch (IllegalStateException e) {
            System.err.println("❌ Business logic error in CarRentalService.save(): " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("❌ Unexpected error in CarRentalService.save(): " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to save car rental: " + e.getMessage(), e);
        }
    }

    public CarRental update(CarRental carRental) {
        validateCarRental(carRental);

        if (carRental.getRentalID() == null) {
            throw new IllegalArgumentException("Rental ID cannot be null for update operation");
        }

        return carRentalRepository.update(carRental);
    }

    private void validateCarRental(CarRental carRental) {
        if (carRental == null) {
            throw new IllegalArgumentException("Car rental cannot be null");
        }

        if (carRental.getCustomer() == null || carRental.getCustomer().getCustomerID() == null) {
            throw new IllegalArgumentException("Customer information is required");
        }

        if (carRental.getCar() == null || carRental.getCar().getCarID() == null) {
            throw new IllegalArgumentException("Car information is required");
        }

        if (carRental.getPickupDate() == null || carRental.getReturnDate() == null) {
            throw new IllegalArgumentException("Pickup date and return date are required");
        }

        if (carRental.getPickupDate().isAfter(carRental.getReturnDate()) ||
            carRental.getPickupDate().isEqual(carRental.getReturnDate())) {
            throw new IllegalArgumentException("Pickup date must be before return date");
        }


        if (carRental.getRentPrice() == null || carRental.getRentPrice() <= 0) {
            throw new IllegalArgumentException("Rent price must be positive");
        }

        if (carRental.getStatus() == null || carRental.getStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("Status is required");
        }
    }

    /**
     * Test database connection and basic functionality
     */
    public boolean testConnection() {
        try {
            System.out.println("Testing CarRentalService database connection...");
            List<CarRental> allRentals = carRentalRepository.findAll();
            System.out.println("✓ Successfully retrieved " + allRentals.size() + " rental records");
            return true;
        } catch (Exception e) {
            System.err.println("❌ Database connection test failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}

