package com.example.fucarrentingsystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "CarProducer")
public class CarProducer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProducerID")
    private Integer producerID;

    @Column(name = "ProducerName", nullable = false)
    private String producerName;

    @Column(name = "Address", nullable = false)
    private String address;

    @Column(name = "Country", nullable = false)
    private String country;

    // Constructors
    public CarProducer() {}

    public CarProducer(String producerName, String address, String country) {
        this.producerName = producerName;
        this.address = address;
        this.country = country;
    }

    // Getters and Setters
    public Integer getProducerID() {
        return producerID;
    }

    public void setProducerID(Integer producerID) {
        this.producerID = producerID;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return producerName + " (" + country + ")";
    }
}

