# FU Car Renting System - Database Setup

## Thông tin Database
- **Tên Database**: FUCarRentingSystemDB
- **User**: root
- **Password**: (để trống)
- **Host**: localhost:3306

## Lệnh SQL để tạo Database và Insert dữ liệu mẫu

### 1. Tạo Database (Tự động tạo qua JPA nếu chưa có)

```sql
CREATE DATABASE IF NOT EXISTS FUCarRentingSystemDB;
USE FUCarRentingSystemDB;
```

### 2. Các bảng sẽ được tự động tạo bởi Hibernate với cấu trúc:

#### Bảng Account
```sql
CREATE TABLE Account (
    AccountID INT PRIMARY KEY AUTO_INCREMENT,
    AccountName VARCHAR(255) NOT NULL,
    Role VARCHAR(50) NOT NULL
);
```

#### Bảng CarProducer
```sql
CREATE TABLE CarProducer (
    ProducerID INT PRIMARY KEY AUTO_INCREMENT,
    ProducerName VARCHAR(255) NOT NULL,
    Address VARCHAR(255) NOT NULL,
    Country VARCHAR(100) NOT NULL
);
```

#### Bảng Customer
```sql
CREATE TABLE Customer (
    CustomerID INT PRIMARY KEY AUTO_INCREMENT,
    CustomerName VARCHAR(255) NOT NULL,
    Mobile VARCHAR(20) NOT NULL,
    Birthday DATE NOT NULL,
    IdentityCard VARCHAR(50) NOT NULL,
    LicenceNumber VARCHAR(50) NOT NULL,
    LicenceDate DATE NOT NULL,
    Email VARCHAR(255) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    AccountID INT NOT NULL,
    FOREIGN KEY (AccountID) REFERENCES Account(AccountID)
);
```

#### Bảng Car
```sql
CREATE TABLE Car (
    CarID INT PRIMARY KEY AUTO_INCREMENT,
    CarName VARCHAR(255) NOT NULL,
    CarModelYear INT NOT NULL,
    Color VARCHAR(50) NOT NULL,
    Capacity INT NOT NULL,
    Description TEXT NOT NULL,
    ImportDate DATE NOT NULL,
    ProducerID INT NOT NULL,
    RentPrice DOUBLE NOT NULL,
    Status VARCHAR(50) NOT NULL,
    FOREIGN KEY (ProducerID) REFERENCES CarProducer(ProducerID)
);
```

#### Bảng CarRental
```sql
CREATE TABLE CarRental (
    RentalID INT PRIMARY KEY AUTO_INCREMENT,
    CustomerID INT NOT NULL,
    CarID INT NOT NULL,
    PickupDate DATE NOT NULL,
    ReturnDate DATE NOT NULL,
    RentPrice DOUBLE NOT NULL,
    Status VARCHAR(50) NOT NULL,
    FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID),
    FOREIGN KEY (CarID) REFERENCES Car(CarID),
    CHECK (PickupDate < ReturnDate)
);
```

#### Bảng Review
```sql
CREATE TABLE Review (
    CustomerID INT NOT NULL,
    CarID INT NOT NULL,
    ReviewStar INT NOT NULL,
    Comment TEXT NOT NULL,
    PRIMARY KEY (CustomerID, CarID),
    FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID),
    FOREIGN KEY (CarID) REFERENCES Car(CarID)
);
```

### 3. Insert Dữ liệu mẫu

#### Tạo Accounts
```sql
INSERT INTO Account (AccountName, Role) VALUES 
('admin', 'Admin'),
('customer', 'Customer');
```

#### Tạo Car Producers
```sql
INSERT INTO CarProducer (ProducerName, Address, Country) VALUES 
('Toyota Motor Corporation', 'Toyota City, Aichi', 'Japan'),
('Honda Motor Co.', 'Tokyo', 'Japan'),
('Ford Motor Company', 'Dearborn, Michigan', 'USA'),
('BMW Group', 'Munich', 'Germany'),
('Mercedes-Benz', 'Stuttgart', 'Germany');
```

#### Tạo Customers (Admin và Customer mẫu)
```sql
-- Admin user: email=admin@fu.com, password=admin123
INSERT INTO Customer (CustomerName, Mobile, Birthday, IdentityCard, LicenceNumber, LicenceDate, Email, Password, AccountID) 
VALUES ('Admin User', '0901234567', '1990-01-01', '001234567890', 'DL001234', '2015-01-01', 'admin@fu.com', 'admin123', 1);

-- Customer user: email=customer@fu.com, password=customer123
INSERT INTO Customer (CustomerName, Mobile, Birthday, IdentityCard, LicenceNumber, LicenceDate, Email, Password, AccountID) 
VALUES ('John Doe', '0912345678', '1995-05-15', '009876543210', 'DL009876', '2018-05-01', 'customer@fu.com', 'customer123', 2);

-- Thêm một số customers khác
INSERT INTO Customer (CustomerName, Mobile, Birthday, IdentityCard, LicenceNumber, LicenceDate, Email, Password, AccountID) 
VALUES 
('Jane Smith', '0923456789', '1992-08-20', '001122334455', 'DL001122', '2017-03-15', 'jane.smith@fu.com', 'jane123', 2),
('Mike Johnson', '0934567890', '1988-12-10', '005566778899', 'DL005566', '2016-07-20', 'mike.johnson@fu.com', 'mike123', 2);
```

#### Tạo Cars
```sql
INSERT INTO Car (CarName, CarModelYear, Color, Capacity, Description, ImportDate, ProducerID, RentPrice, Status) 
VALUES 
('Mazda 3', 2024, 'Red', 5, 'Sedan nhỏ gọn, thiết kế KODO đẹp mắt, lái thể thao', '2024-05-01', 6, 550000, 'Available'),
('Kia Seltos', 2023, 'Orange', 5, 'SUV đô thị cá tính, nhiều tiện nghi', '2023-12-10', 7, 600000, 'Available'),
('Hyundai Accent', 2022, 'Brown', 5, 'Sedan giá rẻ, phổ thông, bền bỉ', '2022-10-25', 8, 300000, 'Rented'),
('Porsche 911', 2024, 'Yellow', 2, 'Xe thể thao hiệu suất cao, siêu tốc độ', '2024-06-15', 9, 5000000, 'Available'),
('Land Rover Defender', 2023, 'Green', 7, 'SUV địa hình huyền thoại, khả năng off-road vượt trội', '2023-09-01', 10, 2000000, 'Maintenance'),
('Suzuki Swift', 2023, 'Blue', 4, 'Hatchback nhỏ gọn, linh hoạt trong phố', '2023-07-20', 11, 400000, 'Available'),
('VinFast Fadil', 2021, 'Gray', 5, 'Xe đô thị cỡ nhỏ, thương hiệu Việt', '2021-11-11', 12, 380000, 'Available');
```

#### Tạo Car Rentals (Lịch sử thuê xe)
```sql
INSERT INTO CarRental (CustomerID, CarID, PickupDate, ReturnDate, RentPrice, Status) 
VALUES 
(5, 6, '2024-03-20', '2024-03-25', 2750000, 'Completed'),
(6, 7, '2024-04-05', '2024-04-10', 3000000, 'Completed'),
(5, 8, '2024-05-15', '2024-05-20', 1500000, 'Completed'),
(7, 9, '2024-06-01', '2024-06-03', 10000000, 'Completed'),
(8, 11, '2024-11-10', '2024-11-17', 2800000, 'Active');
```

#### Tạo Reviews
```sql
INSERT INTO Review (CustomerID, CarID, ReviewStar, Comment) 
VALUES 
(5, 6, 5, 'Mazda 3 rất đẹp và hiện đại, lái rất thích!'),
(6, 7, 4, 'Seltos rộng rãi, phù hợp với gia đình nhỏ. Giá thuê hợp lý.'),
(5, 8, 3, 'Accent là lựa chọn kinh tế, nhưng xe hơi cũ một chút.'),
(7, 9, 5, 'Trải nghiệm siêu xe tuyệt vời! Đáng từng xu.'),
(8, 11, 4, 'Swift nhỏ gọn, đi lại trong thành phố rất tiện. Dịch vụ nhanh chóng.');
```

## Hướng dẫn chạy project

### Bước 1: Khởi động MySQL/phpMyAdmin
- Khởi động XAMPP hoặc MySQL Server
- Đảm bảo MySQL đang chạy trên port 3306

### Bước 2: (Tùy chọn) Tạo database và insert dữ liệu mẫu
- Mở phpMyAdmin (http://localhost/phpmyadmin)
- Chạy các câu lệnh SQL ở trên trong tab SQL
- **Lưu ý**: Database sẽ tự động được tạo bởi Hibernate với cấu hình `hibernate.hbm2ddl.auto=update`

### Bước 3: Build và chạy project
```bash
# Compile project
mvn clean compile

# Run project
mvn javafx:run
```

### Bước 4: Đăng nhập
**Tài khoản Admin:**
- Email: admin@fu.com
- Password: admin123

**Tài khoản Customer:**
- Email: customer@fu.com
- Password: customer123

## Cấu trúc Kiến trúc 3 Lớp

### 1. Presentation Layer (JavaFX Controllers)
- `LoginController.java` - Xử lý đăng nhập
- `AdminDashboardController.java` - Giao diện quản lý cho Admin
- `CustomerDashboardController.java` - Giao diện cho Customer

### 2. Service Layer (Business Logic)
- `AuthService.java` - Xác thực người dùng
- `CarService.java` - Logic nghiệp vụ quản lý xe (bao gồm quy tắc xóa xe đặc biệt)
- `CustomerService.java` - Logic quản lý khách hàng
- `CarRentalService.java` - Logic quản lý thuê xe và báo cáo
- `CarProducerService.java` - Logic quản lý nhà sản xuất

### 3. Repository Layer (Data Access)
- `AccountRepository.java`
- `CarRepository.java`
- `CustomerRepository.java`
- `CarRentalRepository.java`
- `CarProducerRepository.java`

## Các chức năng đã implement

### Admin Features:
✅ Quản lý thông tin khách hàng (CRUD)
✅ Quản lý thông tin xe (CRUD)
✅ Quy tắc xóa xe đặc biệt: Chỉ xóa được xe chưa có lịch sử thuê
✅ Quản lý giao dịch cho thuê
✅ Tạo báo cáo thống kê theo khoảng thời gian (sắp xếp giảm dần)

### Customer Features:
✅ Quản lý hồ sơ cá nhân
✅ Xem lịch sử giao dịch thuê xe

## Lưu ý quan trọng
1. **Quy tắc xóa xe**: Xe chỉ có thể xóa nếu chưa có lịch sử thuê. Nếu đã có lịch sử, chỉ có thể thay đổi Status.
2. **Validation**: PickupDate phải nhỏ hơn ReturnDate trong CarRental.
3. **Password**: Trong production nên mã hóa password, hiện tại đang lưu plain text để đơn giản.

## Troubleshooting

### Lỗi kết nối database:
- Kiểm tra MySQL đã khởi động chưa
- Kiểm tra username/password trong persistence.xml
- Kiểm tra port 3306 có bị chiếm không

### Lỗi JavaFX:
- Đảm bảo đã cài đặt JavaFX SDK
- Kiểm tra Java version (nên dùng Java 17)

### Lỗi Hibernate:
- Kiểm tra file persistence.xml có đúng vị trí không (src/main/resources/META-INF/)
- Kiểm tra các entity class có được khai báo đúng không

