package com.example.fucarrentingsystem.repository;

import com.example.fucarrentingsystem.entity.CarProducer;
import com.example.fucarrentingsystem.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class CarProducerRepository {

    public Optional<CarProducer> findById(Integer id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            CarProducer producer = em.find(CarProducer.class, id);
            return Optional.ofNullable(producer);
        } finally {
            em.close();
        }
    }

    public List<CarProducer> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery("SELECT cp FROM CarProducer cp", CarProducer.class).getResultList();
        } finally {
            em.close();
        }
    }

    public CarProducer save(CarProducer producer) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (producer.getProducerID() == null) {
                em.persist(producer);
            } else {
                producer = em.merge(producer);
            }
            em.getTransaction().commit();
            return producer;
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
            CarProducer producer = em.find(CarProducer.class, id);
            if (producer != null) {
                em.remove(producer);
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
}
