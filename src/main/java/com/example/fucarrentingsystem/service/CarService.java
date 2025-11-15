package com.example.fucarrentingsystem.service;

import com.example.fucarrentingsystem.entity.Car;
import com.example.fucarrentingsystem.repository.CarRepository;

import java.util.List;
import java.util.Optional;

public class CarService {
    private final CarRepository carRepository;

    public CarService() {
        this.carRepository = new CarRepository();
    }

    public Optional<Car> findById(Integer id) {
        return carRepository.findById(id);
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public List<Car> findByStatus(String status) {
        return carRepository.findByStatus(status);
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public Car update(Car car) {
        return carRepository.save(car);
    }

    /**
     * Delete car with special business rule:
     * - Can only delete if car has no rental history
     * - If car has rental history, can only update status
     */
    public boolean deleteCar(Integer carId) throws IllegalStateException {
        if (carRepository.hasRentalHistory(carId)) {
            throw new IllegalStateException(
                "Cannot delete car with rental history. You can only update the status.");
        }

        carRepository.delete(carId);
        return true;
    }

    /**
     * Update only the status of a car (for cars with rental history)
     */
    public Car updateCarStatus(Integer carId, String newStatus) {
        Optional<Car> carOpt = carRepository.findById(carId);
        if (carOpt.isPresent()) {
            Car car = carOpt.get();
            car.setStatus(newStatus);
            return carRepository.save(car);
        }
        throw new IllegalArgumentException("Car not found with ID: " + carId);
    }

    public boolean hasRentalHistory(Integer carId) {
        return carRepository.hasRentalHistory(carId);
    }
}

