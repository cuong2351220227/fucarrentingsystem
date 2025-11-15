package com.example.fucarrentingsystem.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "CarRental")
@IdClass(CarRentalId.class)
public class CarRental {
    @Id
    @ManyToOne
    @JoinColumn(name = "CustomerID", nullable = false)
    private Customer customer;

    @Id
    @ManyToOne
    @JoinColumn(name = "CarID", nullable = false)
    private Car car;

    @Id
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
}

// Composite Key Class
class CarRentalId implements Serializable {
    private Integer customer;
    private Integer car;
    private LocalDate pickupDate;

    public CarRentalId() {}

    public CarRentalId(Integer customer, Integer car, LocalDate pickupDate) {
        this.customer = customer;
        this.car = car;
        this.pickupDate = pickupDate;
    }

    // Getters, Setters, equals and hashCode
    public Integer getCustomer() {
        return customer;
    }

    public void setCustomer(Integer customer) {
        this.customer = customer;
    }

    public Integer getCar() {
        return car;
    }

    public void setCar(Integer car) {
        this.car = car;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarRentalId that = (CarRentalId) o;
        return customer.equals(that.customer) &&
               car.equals(that.car) &&
               pickupDate.equals(that.pickupDate);
    }

    @Override
    public int hashCode() {
        return customer.hashCode() + car.hashCode() + pickupDate.hashCode();
    }
}

