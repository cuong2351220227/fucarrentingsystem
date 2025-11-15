package com.example.fucarrentingsystem.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Review")
@IdClass(ReviewId.class)
public class Review {
    @Id
    @ManyToOne
    @JoinColumn(name = "CustomerID", nullable = false)
    private Customer customer;

    @Id
    @ManyToOne
    @JoinColumn(name = "CarID", nullable = false)
    private Car car;

    @Column(name = "ReviewStar", nullable = false)
    private Integer reviewStar;

    @Column(name = "Comment", nullable = false, length = 1000)
    private String comment;

    // Constructors
    public Review() {}

    public Review(Customer customer, Car car, Integer reviewStar, String comment) {
        this.customer = customer;
        this.car = car;
        this.reviewStar = reviewStar;
        this.comment = comment;
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

    public Integer getReviewStar() {
        return reviewStar;
    }

    public void setReviewStar(Integer reviewStar) {
        this.reviewStar = reviewStar;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

// Composite Key Class
class ReviewId implements Serializable {
    private Integer customer;
    private Integer car;

    public ReviewId() {}

    public ReviewId(Integer customer, Integer car) {
        this.customer = customer;
        this.car = car;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewId reviewId = (ReviewId) o;
        return customer.equals(reviewId.customer) && car.equals(reviewId.car);
    }

    @Override
    public int hashCode() {
        return customer.hashCode() + car.hashCode();
    }
}
