package com.example.fucarrentingsystem.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerID")
    private Integer customerID;

    @Column(name = "CustomerName", nullable = false)
    private String customerName;

    @Column(name = "Mobile", nullable = false)
    private String mobile;

    @Column(name = "Birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "IdentityCard", nullable = false)
    private String identityCard;

    @Column(name = "LicenceNumber", nullable = false)
    private String licenceNumber;

    @Column(name = "LicenceDate", nullable = false)
    private LocalDate licenceDate;

    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @Column(name = "Password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "AccountID", nullable = false)
    private Account account;

    // Constructors
    public Customer() {}

    public Customer(String customerName, String mobile, LocalDate birthday,
                   String identityCard, String licenceNumber, LocalDate licenceDate,
                   String email, String password, Account account) {
        this.customerName = customerName;
        this.mobile = mobile;
        this.birthday = birthday;
        this.identityCard = identityCard;
        this.licenceNumber = licenceNumber;
        this.licenceDate = licenceDate;
        this.email = email;
        this.password = password;
        this.account = account;
    }

    // Getters and Setters
    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public LocalDate getLicenceDate() {
        return licenceDate;
    }

    public void setLicenceDate(LocalDate licenceDate) {
        this.licenceDate = licenceDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}

