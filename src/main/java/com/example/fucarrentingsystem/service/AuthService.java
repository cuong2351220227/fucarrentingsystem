package com.example.fucarrentingsystem.service;

import com.example.fucarrentingsystem.entity.Customer;
import com.example.fucarrentingsystem.repository.CustomerRepository;

import java.util.Optional;

public class AuthService {

    private CustomerRepository customerRepository;

    public AuthService() {
        this.customerRepository = new CustomerRepository();
    }

    public Customer authenticateCustomer(String email, String password) {
        try {
            Optional<Customer> customerOpt = customerRepository.findByEmail(email);
            if (customerOpt.isPresent() && customerOpt.get().getPassword().equals(password)) {
                return customerOpt.get();
            }
            return null;
        } catch (Exception e) {
            System.err.println("Authentication failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean isAdmin(String email, String password) {
        return "admin@fu.com".equals(email) && "admin123".equals(password);
    }
}
