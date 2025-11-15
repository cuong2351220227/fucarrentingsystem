package com.example.fucarrentingsystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountID")
    private Integer accountID;

    @Column(name = "AccountName", nullable = false)
    private String accountName;

    @Column(name = "Role", nullable = false)
    private String role;

    // Constructors
    public Account() {}

    public Account(String accountName, String role) {
        this.accountName = accountName;
        this.role = role;
    }

    // Getters and Setters
    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

