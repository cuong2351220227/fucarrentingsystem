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
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT cr FROM CarRental cr WHERE cr.customer.customerID = :customerId",
                    CarRental.class)
                    .setParameter("customerId", customerId)
                    .getResultList();
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
            return carRental;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
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
            return carRental;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}

