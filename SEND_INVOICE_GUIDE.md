# 🚀 Chức năng Gửi Hóa Đơn Email - Hướng dẫn Nhanh

## ✅ Hoàn thành

- ✅ Tạo `EmailService.java` - Service gửi hóa đơn
- ✅ Thêm nút "Gửi hóa đơn" trong bảng lịch sử thuê xe
- ✅ Lấy dữ liệu từ database
- ✅ Gửi email hóa đơn đơn giản đến email khách hàng
- ✅ Build thành công (Maven compile OK)

## 📧 Email Template

Email gửi đi sẽ chứa:
- **Header**: FU Car Rental System
- **Thông tin khách hàng**: Tên, email, SĐT, CMND
- **Thông tin xe**: Tên, hãng, năm SX, màu, số chỗ
- **Chi tiết thuê xe**: Ngày nhận/trả, số ngày, giá/ngày
- **Tổng tiền**: Nổi bật
- **Footer**: Thông tin liên hệ

## ⚙️ Cấu hình Email (BẮT BUỘC)

### Bước 1: Sửa file `EmailService.java`

Dòng 17-19, thay đổi:

```java
private static final String SENDER_EMAIL = "your-email@gmail.com";      // ← THAY EMAIL
private static final String SENDER_PASSWORD = "your-app-password";      // ← THAY PASSWORD
```

### Bước 2: Lấy Gmail App Password

1. Vào: https://myaccount.google.com/security
2. Bật **2-Step Verification**
3. Tìm **App passwords**
4. Tạo password cho "Mail"
5. Copy 16 ký tự (bỏ dấu cách)
6. Paste vào `SENDER_PASSWORD`

## 🎯 Cách sử dụng

### Khách hàng:

1. Đăng nhập vào Dashboard
2. Vào tab **"Lịch sử thuê xe"**
3. Tìm đơn thuê xe
4. Click nút **"Gửi hóa đơn"** 
5. Xác nhận email
6. Đợi email đến (kiểm tra Spam nếu không thấy)

## 📝 Files thay đổi

```
✏️ Modified:
- src/main/java/com/example/fucarrentingsystem/controller/CustomerDashboardController.java
  → Thêm: EmailService import, field, method handleSendInvoice()
  → Setup nút "Gửi hóa đơn" trong bảng
  
- src/main/resources/com/example/fucarrentingsystem/customer-dashboard.fxml
  → Thêm cột "Hành động" vào bảng lịch sử

🆕 Created:
- src/main/java/com/example/fucarrentingsystem/service/EmailService.java
  → Service gửi hóa đơn qua email
  → Template HTML đơn giản nhưng chuyên nghiệp
```

## 🔒 Bảo mật

⚠️ **QUAN TRỌNG:**
- KHÔNG commit email/password thật lên Git
- Sử dụng App Password (không phải mật khẩu chính)
- Xem xét dùng Environment Variables cho production

```java
// Production - sử dụng environment variables
private static final String SENDER_EMAIL = System.getenv("EMAIL_USERNAME");
private static final String SENDER_PASSWORD = System.getenv("EMAIL_PASSWORD");
```

## ❓ Troubleshooting

### "Không thể gửi email"
- Kiểm tra Internet
- Kiểm tra email/password trong `EmailService.java`
- Đảm bảo dùng App Password

### "Email không đến"
- Kiểm tra thư mục Spam
- Xác nhận email address đúng
- Thử lại sau vài phút

### Lỗi compile
- Chạy: `.\mvnw.cmd clean compile`
- Reload Maven project trong IDE

## 📊 Chi tiết Implementation

### EmailService.java
- `sendRentalInvoice()`: Gửi hóa đơn
- `buildSimpleInvoiceHtml()`: Tạo HTML email
- `createSession()`: Cấu hình SMTP

### CustomerDashboardController.java
- Thêm `EmailService emailService`
- Setup nút trong `setupRentalHistoryTable()`
- `handleSendInvoice()`: Xử lý gửi email
  - Validate email
  - Xác nhận trước gửi
  - Loading indicator
  - Error handling

### customer-dashboard.fxml
- Thêm `<TableColumn fx:id="rentalActionColumn">`

## 🎊 Hoàn tất!

Tất cả đã sẵn sàng. Chỉ cần:
1. Cấu hình email/password trong `EmailService.java`
2. Build & run
3. Khách hàng có thể gửi hóa đơn ngay!

---

**Build Status**: ✅ SUCCESS
**Compile**: ✅ OK
**Ready to Use**: ✅ YES (cần cấu hình email)

