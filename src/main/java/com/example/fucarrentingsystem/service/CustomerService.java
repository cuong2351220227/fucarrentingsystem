package com.example.fucarrentingsystem.service;

import com.example.fucarrentingsystem.entity.Customer;
import com.example.fucarrentingsystem.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService() {
        this.customerRepository = new CustomerRepository();
    }

    public Optional<Customer> findById(Integer id) {
        return customerRepository.findById(id);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customer save(Customer customer) {
        // Check if email already exists
        Optional<Customer> existing = customerRepository.findByEmail(customer.getEmail());
        if (existing.isPresent() && !existing.get().getCustomerID().equals(customer.getCustomerID())) {
            throw new IllegalArgumentException("Email already exists");
        }
        return customerRepository.save(customer);
    }

    public Customer update(Customer customer) {
        return customerRepository.save(customer);
    }

    public void delete(Integer id) {
        customerRepository.delete(id);
    }

    public Customer updateProfile(Customer customer) {
        return customerRepository.save(customer);
    }
}

