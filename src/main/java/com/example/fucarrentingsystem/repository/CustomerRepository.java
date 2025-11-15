package com.example.fucarrentingsystem.repository;

import com.example.fucarrentingsystem.entity.Customer;
import com.example.fucarrentingsystem.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Optional;

public class CustomerRepository {

    public Optional<Customer> findById(Integer id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Customer customer = em.find(Customer.class, id);
            return Optional.ofNullable(customer);
        } finally {
            em.close();
        }
    }

    public List<Customer> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Optional<Customer> findByEmail(String email) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Customer customer = em.createQuery(
                    "SELECT c FROM Customer c WHERE c.email = :email", Customer.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(customer);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    public Optional<Customer> findByEmailAndPassword(String email, String password) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Customer customer = em.createQuery(
                    "SELECT c FROM Customer c WHERE c.email = :email AND c.password = :password",
                    Customer.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();
            return Optional.of(customer);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    public Customer save(Customer customer) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (customer.getCustomerID() == null) {
                em.persist(customer);
            } else {
                customer = em.merge(customer);
            }
            em.getTransaction().commit();
            return customer;
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
            Customer customer = em.find(Customer.class, id);
            if (customer != null) {
                em.remove(customer);
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

