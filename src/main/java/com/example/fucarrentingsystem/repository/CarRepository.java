package com.example.fucarrentingsystem.repository;

import com.example.fucarrentingsystem.entity.Car;
import com.example.fucarrentingsystem.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class CarRepository {

    public Optional<Car> findById(Integer id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Car car = em.find(Car.class, id);
            return Optional.ofNullable(car);
        } finally {
            em.close();
        }
    }

    public List<Car> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Car c", Car.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Car> findByStatus(String status) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Car c WHERE c.status = :status", Car.class)
                    .setParameter("status", status)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Car save(Car car) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (car.getCarID() == null) {
                em.persist(car);
            } else {
                car = em.merge(car);
            }
            em.getTransaction().commit();
            return car;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void delete(Integer id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Car car = em.find(Car.class, id);
            if (car != null) {
                em.remove(car);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public boolean hasRentalHistory(Integer carId) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Long count = em.createQuery(
                    "SELECT COUNT(cr) FROM CarRental cr WHERE cr.car.carID = :carId", Long.class)
                    .setParameter("carId", carId)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }
}

