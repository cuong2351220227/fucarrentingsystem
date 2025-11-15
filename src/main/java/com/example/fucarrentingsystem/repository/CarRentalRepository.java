package com.example.fucarrentingsystem.repository;

import com.example.fucarrentingsystem.entity.CarRental;
import com.example.fucarrentingsystem.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

public class CarRentalRepository {

    public List<CarRental> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery("SELECT cr FROM CarRental cr", CarRental.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<CarRental> findByCustomerId(Integer customerId) {
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }

        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT cr FROM CarRental cr WHERE cr.customer.customerID = :customerId",
                    CarRental.class)
                    .setParameter("customerId", customerId)
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Error finding rentals by customer ID " + customerId + ": " + e.getMessage());
            throw new RuntimeException("Failed to find rentals for customer: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public List<CarRental> findByDateRange(LocalDate startDate, LocalDate endDate) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT cr FROM CarRental cr WHERE cr.pickupDate >= :startDate AND cr.returnDate <= :endDate ORDER BY cr.rentPrice DESC",
                    CarRental.class)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<CarRental> findByCarId(Integer carId) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT cr FROM CarRental cr WHERE cr.car.carID = :carId",
                    CarRental.class)
                    .setParameter("carId", carId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public CarRental save(CarRental carRental) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(carRental);
            em.getTransaction().commit();
            System.out.println("CarRental saved successfully with ID: " + carRental.getRentalID());
            return carRental;
        } catch (Exception e) {
            System.err.println("Error saving CarRental: " + e.getMessage());
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                System.out.println("Transaction rolled back due to error");
            }
            throw new RuntimeException("Failed to save car rental: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public CarRental update(CarRental carRental) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            carRental = em.merge(carRental);
            em.getTransaction().commit();
            System.out.println("CarRental updated successfully with ID: " + carRental.getRentalID());
            return carRental;
        } catch (Exception e) {
            System.err.println("Error updating CarRental: " + e.getMessage());
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                System.out.println("Transaction rolled back due to error");
            }
            throw new RuntimeException("Failed to update car rental: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}

