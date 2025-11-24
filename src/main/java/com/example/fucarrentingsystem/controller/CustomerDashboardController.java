package com.example.fucarrentingsystem.controller;

import com.example.fucarrentingsystem.entity.Car;
import com.example.fucarrentingsystem.entity.CarRental;
import com.example.fucarrentingsystem.entity.Customer;
import com.example.fucarrentingsystem.service.CarRentalService;
import com.example.fucarrentingsystem.service.CarService;
import com.example.fucarrentingsystem.service.CustomerService;
import com.example.fucarrentingsystem.service.EmailService;
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
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

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

    // Car Rental Tab
    @FXML
    private TextField carSearchField;
    @FXML
    private ComboBox<String> carStatusFilter;
    @FXML
    private TableView<Car> availableCarsTable;
    @FXML
    private TableColumn<Car, String> carNameColumn;
    @FXML
    private TableColumn<Car, String> carProducerColumn;
    @FXML
    private TableColumn<Car, Integer> carModelYearColumn;
    @FXML
    private TableColumn<Car, String> carColorColumn;
    @FXML
    private TableColumn<Car, Integer> carCapacityColumn;
    @FXML
    private TableColumn<Car, Double> carRentPriceColumn;
    @FXML
    private TableColumn<Car, String> carStatusColumn;
    @FXML
    private TableColumn<Car, Void> carActionColumn;
    
    @FXML
    private Label selectedCarLabel;
    @FXML
    private DatePicker pickupDatePicker;
    @FXML
    private DatePicker returnDatePicker;
    @FXML
    private Label totalDaysLabel;
    @FXML
    private Label pricePerDayLabel;
    @FXML
    private Label totalCostLabel;
    @FXML
    private Button rentCarButton;

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
    @FXML
    private TableColumn<CarRental, Void> rentalActionColumn;

    private final CustomerService customerService;
    private final CarRentalService carRentalService;
    private final CarService carService;
    private final EmailService emailService;
    private Customer currentCustomer;
    private Car selectedCar;
    private final ObservableList<Car> allCars;

    public CustomerDashboardController() {
        this.customerService = new CustomerService();
        this.carRentalService = new CarRentalService();
        this.carService = new CarService();
        this.emailService = new EmailService();
        this.allCars = FXCollections.observableArrayList();
    }

    @FXML
    private void initialize() {
        currentCustomer = SessionManager.getInstance().getCurrentUser();
        if (currentCustomer != null) {
            setupWelcomeMessage();
            loadProfileData();
            setupRentalHistoryTable();
            setupCarRentalTab();
            loadRentalHistory();
            loadAvailableCars();
        }
    }

    private void setupWelcomeMessage() {
        welcomeLabel.setText("Xin chào, " + currentCustomer.getCustomerName());
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

    private void setupCarRentalTab() {
        // Setup car status filter
        carStatusFilter.getItems().addAll("Tất cả", "Có sẵn", "Đã thuê", "Bảo trì");
        carStatusFilter.setValue("Có sẵn");

        // Initialize labels
        selectedCarLabel.setText("Vui lòng chọn xe");
        pricePerDayLabel.setText("0 VND");
        totalDaysLabel.setText("0");
        totalCostLabel.setText("0 VND");

        // Setup available cars table
        carNameColumn.setCellValueFactory(new PropertyValueFactory<>("carName"));
        carProducerColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProducer().getProducerName()));
        carModelYearColumn.setCellValueFactory(new PropertyValueFactory<>("carModelYear"));
        carColorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        carCapacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        carRentPriceColumn.setCellValueFactory(new PropertyValueFactory<>("rentPrice"));
        carStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        // Setup action column with "Select" button
        carActionColumn.setCellFactory(col -> new TableCell<>() {
            private final Button selectButton = new Button("Chọn");

            {
                selectButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
                selectButton.setOnAction(event -> {
                    Car car = getTableView().getItems().get(getIndex());
                    selectCar(car);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Car car = getTableView().getItems().get(getIndex());
                    selectButton.setDisable(!"Available".equals(car.getStatus()));
                    setGraphic(selectButton);
                }
            }
        });
        
        // Setup date pickers listeners
        pickupDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> calculateTotal());
        returnDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> calculateTotal());
        
        // Set minimum dates to today
        pickupDatePicker.setValue(LocalDate.now());
        returnDatePicker.setValue(LocalDate.now().plusDays(1));
    }

    private void loadAvailableCars() {
        List<Car> cars = carService.findAll();
        allCars.setAll(cars);
        filterCars();
    }
    
    private void filterCars() {
        String statusFilter = carStatusFilter.getValue();
        String searchText = carSearchField.getText().toLowerCase().trim();
        
        List<Car> filteredCars = allCars.stream()
            .filter(car -> {
                // Status filter
                String carStatus = car.getStatus();
                String mappedStatus = mapStatusToVietnamese(carStatus);
                if (!"Tất cả".equals(statusFilter) && !statusFilter.equals(mappedStatus)) {
                    return false;
                }
                // Search filter
                return searchText.isEmpty() || car.getCarName().toLowerCase().contains(searchText);
            })
            .collect(Collectors.toList());
            
        availableCarsTable.setItems(FXCollections.observableArrayList(filteredCars));
    }
    
    private String mapStatusToVietnamese(String englishStatus) {
        switch (englishStatus) {
            case "Available": return "Có sẵn";
            case "Rented": return "Đã thuê";
            case "Maintenance": return "Bảo trì";
            default: return englishStatus;
        }
    }

    @FXML
    private void handleSearchCars() {
        filterCars();
    }
    
    @FXML
    private void handleShowAllCars() {
        carSearchField.clear();
        carStatusFilter.setValue("Tất cả");
        filterCars();
    }
    
    private void selectCar(Car car) {
        if (!"Available".equals(car.getStatus())) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Xe này hiện không có sẵn để thuê.");
            return;
        }
        
        selectedCar = car;
        selectedCarLabel.setText(car.getCarName() + " (" + car.getProducer().getProducerName() + ")");
        pricePerDayLabel.setText(String.format("%.0f VND", car.getRentPrice()));
        rentCarButton.setDisable(false);
        calculateTotal();
        
        showAlert(Alert.AlertType.INFORMATION, "Đã chọn xe",
            "Bạn đã chọn: " + car.getCarName() + "\nVui lòng chọn ngày thuê xe.");
    }
    
    @FXML
    private void handleClearSelection() {
        selectedCar = null;
        selectedCarLabel.setText("Vui lòng chọn xe");
        pricePerDayLabel.setText("0 VND");
        totalDaysLabel.setText("0");
        totalCostLabel.setText("0 VND");
        rentCarButton.setDisable(true);
        pickupDatePicker.setValue(LocalDate.now());
        returnDatePicker.setValue(LocalDate.now().plusDays(1));
    }
    
    private void calculateTotal() {
        if (selectedCar == null || pickupDatePicker.getValue() == null || returnDatePicker.getValue() == null) {
            totalDaysLabel.setText("0");
            totalCostLabel.setText("0 VND");
            return;
        }
        
        LocalDate pickup = pickupDatePicker.getValue();
        LocalDate returnDate = returnDatePicker.getValue();
        
        if (pickup.isBefore(LocalDate.now())) {
            pickupDatePicker.setValue(LocalDate.now());
            return;
        }
        
        if (returnDate.isBefore(pickup) || returnDate.isEqual(pickup)) {
            returnDatePicker.setValue(pickup.plusDays(1));
            return;
        }
        
        long days = ChronoUnit.DAYS.between(pickup, returnDate);
        double totalCost = days * selectedCar.getRentPrice();
        
        totalDaysLabel.setText(String.valueOf(days));
        totalCostLabel.setText(String.format("%.0f VND", totalCost));
    }
    
    @FXML
    private void handleRentCar() {
        if (selectedCar == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn xe trước.");
            return;
        }
        
        LocalDate pickup = pickupDatePicker.getValue();
        LocalDate returnDate = returnDatePicker.getValue();
        
        if (pickup == null || returnDate == null) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Vui lòng chọn cả ngày nhận và ngày trả xe.");
            return;
        }
        
        if (pickup.isBefore(LocalDate.now())) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Ngày nhận xe không thể là ngày trong quá khứ.");
            return;
        }
        
        if (returnDate.isBefore(pickup) || returnDate.isEqual(pickup)) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Ngày trả xe phải sau ngày nhận xe.");
            return;
        }
        
        try {
            System.out.println("=== CustomerDashboardController.handleRentCar() Debug ===");
            System.out.println("Current customer: " + (currentCustomer != null ? currentCustomer.getCustomerName() + " (ID: " + currentCustomer.getCustomerID() + ")" : "null"));
            System.out.println("Selected car: " + (selectedCar != null ? selectedCar.getCarName() + " (ID: " + selectedCar.getCarID() + ")" : "null"));
            System.out.println("Pickup date: " + pickup);
            System.out.println("Return date: " + returnDate);

            // Validate customer data
            if (currentCustomer == null || currentCustomer.getCustomerID() == null) {
                System.err.println("❌ Customer validation failed");
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể xác định thông tin khách hàng. Vui lòng đăng nhập lại.");
                return;
            }
            System.out.println("✓ Customer validation passed");

            // Validate car data
            if (selectedCar.getCarID() == null) {
                System.err.println("❌ Car validation failed");
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Thông tin xe không hợp lệ. Vui lòng chọn xe khác.");
                return;
            }
            System.out.println("✓ Car validation passed");

            // Check if car is still available (double-check)
            if (!"Available".equals(selectedCar.getStatus())) {
                showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Xe này đã không còn sẵn có. Vui lòng chọn xe khác.");
                handleClearSelection();
                loadAvailableCars();
                return;
            }

            // Check for existing rental conflicts more thoroughly
            if (carRentalService.hasRentalConflict(selectedCar.getCarID(), pickup, returnDate)) {
                showAlert(Alert.AlertType.WARNING, "Cảnh báo",
                    "Xe này đã được thuê trong khoảng thời gian bạn chọn. Vui lòng chọn thời gian khác.");
                return;
            }

            if (carRentalService.hasCustomerCarConflict(currentCustomer.getCustomerID(),
                    selectedCar.getCarID(), pickup)) {
                showAlert(Alert.AlertType.WARNING, "Cảnh báo",
                    "Bạn đã thuê xe này vào ngày " + pickup + ". Vui lòng chọn ngày khác hoặc xe khác.");
                return;
            }

            // Calculate total cost
            long days = ChronoUnit.DAYS.between(pickup, returnDate);
            double totalCost = days * selectedCar.getRentPrice();
            
            // Validate rental price
            if (totalCost <= 0) {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể tính toán giá thuê xe. Vui lòng thử lại.");
                return;
            }

            // Create rental record with all required fields
            System.out.println("Creating CarRental object...");
            CarRental rental = new CarRental();
            rental.setCustomer(currentCustomer);
            rental.setCar(selectedCar);
            rental.setPickupDate(pickup);
            rental.setReturnDate(returnDate);
            rental.setRentPrice(totalCost);
            rental.setStatus("Active");
            
            System.out.println("CarRental object created:");
            System.out.println("- Customer: " + rental.getCustomer().getCustomerName() + " (ID: " + rental.getCustomer().getCustomerID() + ")");
            System.out.println("- Car: " + rental.getCar().getCarName() + " (ID: " + rental.getCar().getCarID() + ")");
            System.out.println("- Pickup: " + rental.getPickupDate());
            System.out.println("- Return: " + rental.getReturnDate());
            System.out.println("- Price: " + rental.getRentPrice());
            System.out.println("- Status: " + rental.getStatus());

            // Save rental first
            System.out.println("Calling carRentalService.save()...");
            CarRental savedRental = carRentalService.save(rental);

            if (savedRental == null) {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể lưu thông tin thuê xe. Vui lòng thử lại.");
                return;
            }

            // Update car status to Rented
            selectedCar.setStatus("Rented");
            carService.update(selectedCar);
            
            // Show success message
            showAlert(Alert.AlertType.INFORMATION, "Thuê xe thành công",
                String.format("Thuê xe được xác nhận!\n\nXe: %s\nNgày nhận: %s\nNgày trả: %s\nSố ngày: %d\nTổng chi phí: %.0f VND",
                    selectedCar.getCarName(), pickup, returnDate, days, totalCost));

            // Refresh data
            handleClearSelection();
            loadAvailableCars();
            loadRentalHistory();
            
        } catch (IllegalArgumentException e) {
            System.err.println("Validation error: " + e.getMessage());
            showAlert(Alert.AlertType.WARNING, "Lỗi dữ liệu", "Dữ liệu không hợp lệ: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println("Business logic error: " + e.getMessage());
            showAlert(Alert.AlertType.WARNING, "Lỗi nghiệp vụ", e.getMessage());
        } catch (jakarta.persistence.PersistenceException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi cơ sở dữ liệu",
                "Không thể lưu thông tin thuê xe. Có thể do:\n" +
                "- Xe này đã được thuê trong cùng thời gian\n" +
                "- Vấn đề kết nối cơ sở dữ liệu\n" +
                "- Lỗi ràng buộc dữ liệu\n\n" +
                "Chi tiết lỗi: " + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("Runtime error: " + e.getMessage());
            e.printStackTrace();
            String message = e.getMessage();
            if (message != null && message.contains("Failed to")) {
                showAlert(Alert.AlertType.ERROR, "Lỗi hệ thống", message);
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi không xác định",
                    "Đã xảy ra lỗi không mong muốn.\n\n" +
                    "Chi tiết: " + (message != null ? message : "Không có thông tin chi tiết"));
            }
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace(); // Log lỗi để debug
            showAlert(Alert.AlertType.ERROR, "Lỗi",
                "Không thể xử lý thuê xe. Vui lòng thử lại hoặc liên hệ hỗ trợ.\n\n" +
                "Loại lỗi: " + e.getClass().getSimpleName() + "\n" +
                "Chi tiết: " + e.getMessage());
        }
    }

    private void setupRentalHistoryTable() {
        rentalCarColumn.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCar().getCarName()));
        rentalPickupColumn.setCellValueFactory(new PropertyValueFactory<>("pickupDate"));
        rentalReturnColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        rentalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("rentPrice"));
        rentalStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Setup action column with send invoice button
        rentalActionColumn.setCellFactory(col -> new TableCell<CarRental, Void>() {
            private final Button sendButton = new Button("Gửi hóa đơn");

            {
                sendButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 5 10;");
                sendButton.setPrefWidth(100);
                sendButton.setOnAction(event -> {
                    CarRental rental = getTableView().getItems().get(getIndex());
                    handleSendInvoice(rental);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : sendButton);
            }
        });
    }

    private void loadRentalHistory() {
        List<CarRental> rentals = carRentalService.findByCustomerId(currentCustomer.getCustomerID());
        rentalHistoryTable.setItems(FXCollections.observableArrayList(rentals));
    }

    @FXML
    private void handleUpdateProfile() {
        try {
            // Validate email format
            String newEmail = profileEmailField.getText().trim();
            if (newEmail.isEmpty() || !newEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                showAlert(Alert.AlertType.WARNING, "Lỗi xác thực", "Email không hợp lệ!");
                return;
            }

            currentCustomer.setCustomerName(profileNameField.getText());
            currentCustomer.setEmail(newEmail);
            currentCustomer.setMobile(profileMobileField.getText());
            currentCustomer.setBirthday(profileBirthdayPicker.getValue());
            currentCustomer.setIdentityCard(profileIdentityCardField.getText());
            currentCustomer.setLicenceNumber(profileLicenceNumberField.getText());
            currentCustomer.setLicenceDate(profileLicenceDatePicker.getValue());
            currentCustomer.setPassword(profilePasswordField.getText());

            customerService.updateProfile(currentCustomer);
            SessionManager.getInstance().setCurrentUser(currentCustomer);

            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Cập nhật hồ sơ thành công");
            setupWelcomeMessage();
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Email đã tồn tại trong hệ thống!");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Lỗi khi cập nhật hồ sơ: " + e.getMessage());
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
            stage.setTitle("Đăng nhập - Hệ thống thuê xe FU");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Lỗi khi đăng xuất: " + e.getMessage());
        }
    }

    /**
     * Gửi hóa đơn qua email
     */
    private void handleSendInvoice(CarRental rental) {
        // Kiểm tra email
        if (currentCustomer.getEmail() == null || currentCustomer.getEmail().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo",
                "Email chưa được cập nhật. Vui lòng cập nhật trong Hồ sơ cá nhân.");
            return;
        }

        // Xác nhận trước khi gửi
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Gửi hóa đơn");
        confirm.setHeaderText("Xác nhận gửi hóa đơn");
        confirm.setContentText("Gửi hóa đơn đến email: " + currentCustomer.getEmail() + " ?");

        if (confirm.showAndWait().get() != ButtonType.OK) {
            return;
        }

        // Hiển thị loading
        Alert loading = new Alert(Alert.AlertType.INFORMATION);
        loading.setTitle("Đang xử lý");
        loading.setHeaderText(null);
        loading.setContentText("Đang gửi hóa đơn...");
        loading.show();

        // Gửi email trong background thread
        new Thread(() -> {
            try {
                boolean success = emailService.sendRentalInvoice(rental, currentCustomer);

                javafx.application.Platform.runLater(() -> {
                    loading.close();
                    if (success) {
                        showAlert(Alert.AlertType.INFORMATION, "Thành công",
                            "Hóa đơn đã được gửi đến:\n" + currentCustomer.getEmail() +
                            "\n\n(Kiểm tra thư mục Spam nếu không thấy)");
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Lỗi",
                            "Không thể gửi email. Kiểm tra lại cấu hình SMTP trong EmailService.java");
                    }
                });
            } catch (Exception e) {
                javafx.application.Platform.runLater(() -> {
                    loading.close();
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Lỗi: " + e.getMessage());
                });
            }
        }).start();
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
        setupCarRentalTab();
        loadRentalHistory();
        loadAvailableCars();
    }
}
