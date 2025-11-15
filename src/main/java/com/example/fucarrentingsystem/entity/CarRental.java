package com.example.fucarrentingsystem.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "CarRental")
public class CarRental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RentalID")
    private Integer rentalID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CustomerID", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CarID", nullable = false)
    private Car car;

    @Column(name = "PickupDate", nullable = false)
    private LocalDate pickupDate;

    @Column(name = "ReturnDate", nullable = false)
    private LocalDate returnDate;

    @Column(name = "RentPrice", nullable = false)
    private Double rentPrice;

    @Column(name = "Status", nullable = false)
    private String status;

    // Constructors
    public CarRental() {}

    public CarRental(Customer customer, Car car, LocalDate pickupDate,
                    LocalDate returnDate, Double rentPrice, String status) {
        this.customer = customer;
        this.car = car;
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;
        this.rentPrice = rentPrice;
        this.status = status;
    }

    // Getters and Setters
    public Integer getRentalID() {
        return rentalID;
    }

    public void setRentalID(Integer rentalID) {
        this.rentalID = rentalID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
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

    @Override
    public String toString() {
        return "CarRental{" +
                "rentalID=" + rentalID +
                ", customer=" + (customer != null ? customer.getCustomerName() : "null") +
                ", car=" + (car != null ? car.getCarName() : "null") +
                ", pickupDate=" + pickupDate +
                ", returnDate=" + returnDate +
                ", rentPrice=" + rentPrice +
                ", status='" + status + '\'' +
                '}';
    }
}

