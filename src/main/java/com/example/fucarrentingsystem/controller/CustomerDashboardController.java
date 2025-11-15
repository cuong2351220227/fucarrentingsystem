package com.example.fucarrentingsystem.controller;

import com.example.fucarrentingsystem.entity.CarRental;
import com.example.fucarrentingsystem.entity.Customer;
import com.example.fucarrentingsystem.service.CarRentalService;
import com.example.fucarrentingsystem.service.CustomerService;
import com.example.fucarrentingsystem.util.SessionManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class CustomerDashboardController {

    @FXML
    private Label welcomeLabel;

    // Profile Tab
    @FXML
    private TextField profileNameField;
    @FXML
    private TextField profileEmailField;
    @FXML
    private TextField profileMobileField;
    @FXML
    private DatePicker profileBirthdayPicker;
    @FXML
    private TextField profileIdentityCardField;
    @FXML
    private TextField profileLicenceNumberField;
    @FXML
    private DatePicker profileLicenceDatePicker;
    @FXML
    private PasswordField profilePasswordField;
    @FXML
    private Button updateProfileButton;

    // Rental History Tab
    @FXML
    private TableView<CarRental> rentalHistoryTable;
    @FXML
    private TableColumn<CarRental, String> rentalCarColumn;
    @FXML
    private TableColumn<CarRental, LocalDate> rentalPickupColumn;
    @FXML
    private TableColumn<CarRental, LocalDate> rentalReturnColumn;
    @FXML
    private TableColumn<CarRental, Double> rentalPriceColumn;
    @FXML
    private TableColumn<CarRental, String> rentalStatusColumn;

    private final CustomerService customerService;
    private final CarRentalService carRentalService;
    private Customer currentCustomer;

    public CustomerDashboardController() {
        this.customerService = new CustomerService();
        this.carRentalService = new CarRentalService();
    }

    @FXML
    private void initialize() {
        currentCustomer = SessionManager.getInstance().getCurrentUser();
        if (currentCustomer != null) {
            setupWelcomeMessage();
            loadProfileData();
            setupRentalHistoryTable();
            loadRentalHistory();
        }
    }

    private void setupWelcomeMessage() {
        welcomeLabel.setText("Welcome, " + currentCustomer.getCustomerName());
    }

    private void loadProfileData() {
        profileNameField.setText(currentCustomer.getCustomerName());
        profileEmailField.setText(currentCustomer.getEmail());
        profileMobileField.setText(currentCustomer.getMobile());
        profileBirthdayPicker.setValue(currentCustomer.getBirthday());
        profileIdentityCardField.setText(currentCustomer.getIdentityCard());
        profileLicenceNumberField.setText(currentCustomer.getLicenceNumber());
        profileLicenceDatePicker.setValue(currentCustomer.getLicenceDate());
        profilePasswordField.setText(currentCustomer.getPassword());
    }

    private void setupRentalHistoryTable() {
        rentalCarColumn.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCar().getCarName()));
        rentalPickupColumn.setCellValueFactory(new PropertyValueFactory<>("pickupDate"));
        rentalReturnColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        rentalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("rentPrice"));
        rentalStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadRentalHistory() {
        List<CarRental> rentals = carRentalService.findByCustomerId(currentCustomer.getCustomerID());
        rentalHistoryTable.setItems(FXCollections.observableArrayList(rentals));
    }

    @FXML
    private void handleUpdateProfile() {
        try {
            currentCustomer.setCustomerName(profileNameField.getText());
            currentCustomer.setMobile(profileMobileField.getText());
            currentCustomer.setBirthday(profileBirthdayPicker.getValue());
            currentCustomer.setIdentityCard(profileIdentityCardField.getText());
            currentCustomer.setLicenceNumber(profileLicenceNumberField.getText());
            currentCustomer.setLicenceDate(profileLicenceDatePicker.getValue());
            currentCustomer.setPassword(profilePasswordField.getText());

            customerService.updateProfile(currentCustomer);
            SessionManager.getInstance().setCurrentUser(currentCustomer);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Profile updated successfully");
            setupWelcomeMessage();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error updating profile: " + e.getMessage());
        }
    }

    @FXML
    private void handleLogout() {
        SessionManager.getInstance().logout();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/fucarrentingsystem/login-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
            stage.setTitle("Login - FU Car Renting System");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error logging out: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setCurrentCustomer(Customer customer) {
        this.currentCustomer = customer;
        SessionManager.getInstance().setCurrentUser(customer);
        setupWelcomeMessage();
        loadProfileData();
        setupRentalHistoryTable();
        loadRentalHistory();
    }
}
