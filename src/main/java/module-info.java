module com.example.fucarrentingsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;

    opens com.example.fucarrentingsystem to javafx.fxml;
    opens com.example.fucarrentingsystem.entity to org.hibernate.orm.core, javafx.base;
    opens com.example.fucarrentingsystem.controller to javafx.fxml;

    exports com.example.fucarrentingsystem;
    exports com.example.fucarrentingsystem.controller;
    exports com.example.fucarrentingsystem.entity;
}