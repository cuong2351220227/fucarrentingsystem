package com.example.fucarrentingsystem.util;

import com.example.fucarrentingsystem.entity.Customer;

public class SessionManager {
    private static SessionManager instance;
    private Customer currentUser;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setCurrentUser(Customer customer) {
        this.currentUser = customer;
    }

    public Customer getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public void logout() {
        currentUser = null;
    }

    public boolean isAdmin() {
        return currentUser != null &&
               currentUser.getAccount().getRole().equalsIgnoreCase("Admin");
    }

    public boolean isCustomer() {
        return currentUser != null &&
               currentUser.getAccount().getRole().equalsIgnoreCase("Customer");
    }
}

