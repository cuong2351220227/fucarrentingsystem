package com.example.fucarrentingsystem.service;

import com.example.fucarrentingsystem.entity.CarProducer;
import com.example.fucarrentingsystem.repository.CarProducerRepository;

import java.util.List;
import java.util.Optional;

public class CarProducerService {
    private final CarProducerRepository carProducerRepository;

    public CarProducerService() {
        this.carProducerRepository = new CarProducerRepository();
    }

    public Optional<CarProducer> findById(Integer id) {
        return carProducerRepository.findById(id);
    }

    public List<CarProducer> findAll() {
        return carProducerRepository.findAll();
    }

    public CarProducer save(CarProducer producer) {
        return carProducerRepository.save(producer);
    }

    public CarProducer update(CarProducer producer) {
        return carProducerRepository.save(producer);
    }

    public void delete(Integer id) {
        carProducerRepository.delete(id);
    }
}
