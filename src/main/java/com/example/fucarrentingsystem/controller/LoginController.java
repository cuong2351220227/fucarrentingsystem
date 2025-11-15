package com.example.fucarrentingsystem.controller;

import com.example.fucarrentingsystem.entity.Customer;
import com.example.fucarrentingsystem.service.AuthService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel;

    private AuthService authService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        authService = new AuthService();
        errorLabel.setText("");
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        // Clear previous error messages
        errorLabel.setText("");

        // Validate input
        if (email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter both email and password.");
            return;
        }

        try {
            // Check for default admin login
            if ("admin@fu.com".equals(email) && "admin123".equals(password)) {
                loadAdminDashboard(event);
                return;
            }

            // Check customer login
            Customer customer = authService.authenticateCustomer(email, password);
            if (customer != null) {
                loadCustomerDashboard(event, customer);
            } else {
                errorLabel.setText("Invalid email or password.");
            }

        } catch (Exception e) {
            errorLabel.setText("Login failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadAdminDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fucarrentingsystem/admin-dashboard.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Admin Dashboard - FU Car Renting System");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            errorLabel.setText("Failed to load admin dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadCustomerDashboard(ActionEvent event, Customer customer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fucarrentingsystem/customer-dashboard.fxml"));
            Scene scene = new Scene(loader.load());

            // Pass customer data to customer dashboard controller
            CustomerDashboardController controller = loader.getController();
            controller.setCurrentCustomer(customer);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Customer Dashboard - FU Car Renting System");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            errorLabel.setText("Failed to load customer dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
