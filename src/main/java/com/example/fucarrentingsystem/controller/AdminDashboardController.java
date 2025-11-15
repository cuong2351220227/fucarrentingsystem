package com.example.fucarrentingsystem.controller;

import com.example.fucarrentingsystem.entity.Car;
import com.example.fucarrentingsystem.entity.CarProducer;
import com.example.fucarrentingsystem.entity.Customer;
import com.example.fucarrentingsystem.service.CarProducerService;
import com.example.fucarrentingsystem.service.CarRentalService;
import com.example.fucarrentingsystem.service.CarService;
import com.example.fucarrentingsystem.service.CustomerService;
import com.example.fucarrentingsystem.util.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.Optional;

public class AdminDashboardController {

    // Tab Pane
    @FXML
    private TabPane mainTabPane;

    // Car Management Tab
    @FXML
    private TableView<Car> carTableView;
    @FXML
    private TableColumn<Car, Integer> carIdColumn;
    @FXML
    private TableColumn<Car, String> carNameColumn;
    @FXML
    private TableColumn<Car, Integer> carYearColumn;
    @FXML
    private TableColumn<Car, String> carColorColumn;
    @FXML
    private TableColumn<Car, String> carStatusColumn;
    @FXML
    private TableColumn<Car, Double> carPriceColumn;

    @FXML
    private TextField carNameField;
    @FXML
    private TextField carYearField;
    @FXML
    private TextField carColorField;
    @FXML
    private TextField carCapacityField;
    @FXML
    private TextArea carDescriptionArea;
    @FXML
    private DatePicker carImportDatePicker;
    @FXML
    private ComboBox<CarProducer> carProducerCombo;
    @FXML
    private TextField carPriceField;
    @FXML
    private ComboBox<String> carStatusCombo;
    @FXML
    private Button saveCarButton;
    @FXML
    private Button deleteCarButton;
    @FXML
    private Button clearCarButton;

    // Customer Management Tab
    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;
    @FXML
    private TableColumn<Customer, String> customerNameColumn;
    @FXML
    private TableColumn<Customer, String> customerEmailColumn;
    @FXML
    private TableColumn<Customer, String> customerMobileColumn;

    // Rental Report Tab
    @FXML
    private DatePicker reportStartDatePicker;
    @FXML
    private DatePicker reportEndDatePicker;
    @FXML
    private Button generateReportButton;
    @FXML
    private TextArea reportTextArea;

    @FXML
    private Label welcomeLabel;

    private final CarService carService;
    private final CustomerService customerService;
    private final CarRentalService carRentalService;
    private final CarProducerService carProducerService;

    private Car selectedCar;

    public AdminDashboardController() {
        this.carService = new CarService();
        this.customerService = new CustomerService();
        this.carRentalService = new CarRentalService();
        this.carProducerService = new CarProducerService();
    }

    @FXML
    private void initialize() {
        setupWelcomeMessage();
        setupCarTable();
        setupCustomerTable();
        loadCarProducers();
        loadCars();
        loadCustomers();
        setupCarStatusCombo();
    }

    private void setupWelcomeMessage() {
        Customer currentUser = SessionManager.getInstance().getCurrentUser();
        if (currentUser != null) {
            welcomeLabel.setText("Welcome, " + currentUser.getCustomerName() + " (Admin)");
        }
    }

    private void setupCarTable() {
        carIdColumn.setCellValueFactory(new PropertyValueFactory<>("carID"));
        carNameColumn.setCellValueFactory(new PropertyValueFactory<>("carName"));
        carYearColumn.setCellValueFactory(new PropertyValueFactory<>("carModelYear"));
        carColorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        carStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        carPriceColumn.setCellValueFactory(new PropertyValueFactory<>("rentPrice"));

        carTableView.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    selectedCar = newSelection;
                    populateCarFields(newSelection);
                }
            }
        );
    }

    private void setupCustomerTable() {
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        customerMobileColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    }

    private void setupCarStatusCombo() {
        ObservableList<String> statuses = FXCollections.observableArrayList(
            "Available", "Rented", "Maintenance", "Inactive"
        );
        carStatusCombo.setItems(statuses);
        carStatusCombo.getSelectionModel().selectFirst();
    }

    private void loadCarProducers() {
        List<CarProducer> producers = carProducerService.findAll();
        carProducerCombo.setItems(FXCollections.observableArrayList(producers));
    }

    private void loadCars() {
        List<Car> cars = carService.findAll();
        carTableView.setItems(FXCollections.observableArrayList(cars));
    }

    private void loadCustomers() {
        List<Customer> customers = customerService.findAll();
        customerTableView.setItems(FXCollections.observableArrayList(customers));
    }

    private void populateCarFields(Car car) {
        carNameField.setText(car.getCarName());
        carYearField.setText(String.valueOf(car.getCarModelYear()));
        carColorField.setText(car.getColor());
        carCapacityField.setText(String.valueOf(car.getCapacity()));
        carDescriptionArea.setText(car.getDescription());
        carImportDatePicker.setValue(car.getImportDate());
        carProducerCombo.setValue(car.getProducer());
        carPriceField.setText(String.valueOf(car.getRentPrice()));
        carStatusCombo.setValue(car.getStatus());
    }

    @FXML
    private void handleSaveCar() {
        try {
            if (selectedCar == null) {
                // Create new car
                Car car = new Car();
                car.setCarName(carNameField.getText());
                car.setCarModelYear(Integer.parseInt(carYearField.getText()));
                car.setColor(carColorField.getText());
                car.setCapacity(Integer.parseInt(carCapacityField.getText()));
                car.setDescription(carDescriptionArea.getText());
                car.setImportDate(carImportDatePicker.getValue());
                car.setProducer(carProducerCombo.getValue());
                car.setRentPrice(Double.parseDouble(carPriceField.getText()));
                car.setStatus(carStatusCombo.getValue());

                carService.save(car);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Car created successfully");
            } else {
                // Update existing car
                selectedCar.setCarName(carNameField.getText());
                selectedCar.setCarModelYear(Integer.parseInt(carYearField.getText()));
                selectedCar.setColor(carColorField.getText());
                selectedCar.setCapacity(Integer.parseInt(carCapacityField.getText()));
                selectedCar.setDescription(carDescriptionArea.getText());
                selectedCar.setImportDate(carImportDatePicker.getValue());
                selectedCar.setProducer(carProducerCombo.getValue());
                selectedCar.setRentPrice(Double.parseDouble(carPriceField.getText()));
                selectedCar.setStatus(carStatusCombo.getValue());

                carService.update(selectedCar);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Car updated successfully");
            }

            clearCarFields();
            loadCars();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error saving car: " + e.getMessage());
        }
    }

    @FXML
    private void handleDeleteCar() {
        if (selectedCar == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a car to delete");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Delete");
        confirmation.setHeaderText("Delete Car");
        confirmation.setContentText("Are you sure you want to delete this car?");

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                carService.deleteCar(selectedCar.getCarID());
                showAlert(Alert.AlertType.INFORMATION, "Success", "Car deleted successfully");
                clearCarFields();
                loadCars();
            } catch (IllegalStateException e) {
                // Car has rental history, can only update status
                showAlert(Alert.AlertType.WARNING, "Cannot Delete",
                    "This car has rental history and cannot be deleted.\n" +
                    "You can only change its status.");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Error deleting car: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleClearCar() {
        clearCarFields();
    }

    private void clearCarFields() {
        selectedCar = null;
        carNameField.clear();
        carYearField.clear();
        carColorField.clear();
        carCapacityField.clear();
        carDescriptionArea.clear();
        carImportDatePicker.setValue(null);
        carProducerCombo.setValue(null);
        carPriceField.clear();
        carStatusCombo.getSelectionModel().selectFirst();
        carTableView.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleGenerateReport() {
        LocalDate startDate = reportStartDatePicker.getValue();
        LocalDate endDate = reportEndDatePicker.getValue();

        if (startDate == null || endDate == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select start and end dates");
            return;
        }

        try {
            var rentals = carRentalService.generateRentalReport(startDate, endDate);

            StringBuilder report = new StringBuilder();
            report.append("=== RENTAL REPORT ===\n");
            report.append("Period: ").append(startDate).append(" to ").append(endDate).append("\n");
            report.append("Total Rentals: ").append(rentals.size()).append("\n\n");
            report.append("Details (sorted by price - descending):\n");
            report.append("-".repeat(80)).append("\n");

            double totalRevenue = 0;
            for (var rental : rentals) {
                report.append(String.format("Customer: %s | Car: %s | Pickup: %s | Return: %s | Price: $%.2f | Status: %s\n",
                    rental.getCustomer().getCustomerName(),
                    rental.getCar().getCarName(),
                    rental.getPickupDate(),
                    rental.getReturnDate(),
                    rental.getRentPrice(),
                    rental.getStatus()
                ));
                totalRevenue += rental.getRentPrice();
            }

            report.append("-".repeat(80)).append("\n");
            report.append(String.format("Total Revenue: $%.2f\n", totalRevenue));

            reportTextArea.setText(report.toString());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error generating report: " + e.getMessage());
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
}

