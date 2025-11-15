# Hướng dẫn Chạy Dự án FU Car Renting System

## Yêu cầu hệ thống
- Java 17 trở lên
- MySQL Server (XAMPP/Standalone)
- Maven (hoặc sử dụng Maven wrapper có sẵn trong project)

## Bước 1: Khởi động MySQL
1. Mở XAMPP Control Panel
2. Start MySQL Service
3. Đảm bảo MySQL đang chạy trên port 3306

## Bước 2: Tạo Database và Insert dữ liệu mẫu (Tùy chọn)
Có 2 cách:

### Cách 1: Tự động (Khuyến nghị)
- Database sẽ tự động được tạo khi chạy ứng dụng lần đầu
- Sau đó import dữ liệu mẫu bằng file `database_setup.sql`

### Cách 2: Thủ công
1. Mở phpMyAdmin: http://localhost/phpmyadmin
2. Import file `database_setup.sql` hoặc chạy các lệnh trong `DATABASE_README.md`

## Bước 3: Chạy ứng dụng

### Chạy bằng Maven Wrapper (Khuyến nghị):
```cmd
mvnw.cmd clean javafx:run
```

### Hoặc chạy bằng Maven:
```cmd
mvn clean javafx:run
```

## Bước 4: Đăng nhập

### Tài khoản Admin:
- **Email**: admin@fu.com
- **Password**: admin123

### Tài khoản Customer:
- **Email**: customer@fu.com
- **Password**: customer123

## Chức năng của Ứng dụng

### Admin có thể:
✅ Quản lý khách hàng (CRUD)
✅ Quản lý xe (CRUD) với quy tắc xóa đặc biệt
✅ Xem danh sách giao dịch thuê xe
✅ Tạo báo cáo thống kê theo khoảng thời gian

### Customer có thể:
✅ Cập nhật thông tin cá nhân
✅ Xem lịch sử thuê xe của mình

## Lưu ý quan trọng
1. **Quy tắc xóa xe**: Xe chỉ có thể xóa nếu chưa có lịch sử thuê. Nếu đã có lịch sử thuê, chỉ được phép thay đổi Status.
2. Dữ liệu mẫu đã có sẵn trong file `database_setup.sql`
3. Hibernate tự động tạo/cập nhật schema database khi chạy ứng dụng

## Troubleshooting

### Lỗi "Cannot connect to database"
- Kiểm tra MySQL đã khởi động chưa
- Kiểm tra port 3306 có đang được sử dụng không
- Kiểm tra username/password trong `persistence.xml`

### Lỗi "Module not found"
- Đảm bảo đã build project: `mvnw.cmd clean compile`
- Kiểm tra Java version: `java -version` (cần Java 17+)

### Lỗi JavaFX
- Đảm bảo JavaFX dependencies đã được download
- Chạy lệnh: `mvnw.cmd clean install`

## Cấu trúc Dự án

```
FUCarRentingSystem/
├── src/main/java/
│   └── com/example/fucarrentingsystem/
│       ├── entity/              # JPA Entities
│       ├── repository/          # Data Access Layer
│       ├── service/             # Business Logic Layer
│       ├── controller/          # Presentation Layer
│       ├── util/                # Utility classes
│       └── HelloApplication.java
├── src/main/resources/
│   ├── META-INF/
│   │   └── persistence.xml     # Hibernate configuration
│   └── com/example/fucarrentingsystem/
│       ├── login-view.fxml
│       ├── admin-dashboard.fxml
│       └── customer-dashboard.fxml
├── database_setup.sql           # SQL script
├── DATABASE_README.md           # Database documentation
└── pom.xml
```

## Liên hệ
Nếu gặp vấn đề, vui lòng kiểm tra:
1. File `DATABASE_README.md` - Hướng dẫn chi tiết về database
2. File `database_setup.sql` - Script SQL đầy đủ
3. Logs trong console khi chạy ứng dụng

