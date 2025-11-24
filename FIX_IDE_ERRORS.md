# 🔧 Hướng dẫn khắc phục lỗi hiển thị trong IntelliJ IDEA

## ✅ Trạng thái dự án

**Project đã build thành công!** Maven compile và package đều thành công.

Các "lỗi" bạn thấy trong IDE chỉ là vấn đề về cache của IntelliJ IDEA chưa nhận diện được thư viện `jakarta.mail`.

---

## 🛠️ Cách khắc phục

### **Phương pháp 1: Reload Maven Project (KHUYẾN NGHỊ)**

1. Mở **Maven tool window**:
   - `View` → `Tool Windows` → `Maven`
   - Hoặc nhấn `Ctrl + Shift + O` (Windows)

2. Click vào icon **"Reload All Maven Projects"**:
   - Icon hình tròn với 2 mũi tên xoắn
   - Nằm ở góc trên bên trái của Maven window

3. Đợi IntelliJ reload xong (~30 giây)

4. Các lỗi sẽ biến mất! ✨

---

### **Phương pháp 2: Invalidate Caches (Nếu cách 1 không hiệu quả)**

1. `File` → `Invalidate Caches / Restart...`
2. Chọn **"Invalidate and Restart"**
3. Đợi IntelliJ khởi động lại
4. Sau khi khởi động, Maven sẽ tự động reimport

---

### **Phương pháp 3: Reimport từ Project Structure**

1. `File` → `Project Structure` (hoặc `Ctrl + Alt + Shift + S`)
2. Chọn **Modules** → **Dependencies** tab
3. Click **"+"** → **Library** → **From Maven...**
4. Nhập: `com.sun.mail:jakarta.mail:2.0.1`
5. Click **OK** và Apply

---

## 📋 Thông tin kỹ thuật

### Đã thực hiện:

✅ Thêm dependency `com.sun.mail:jakarta.mail:2.0.1` vào `pom.xml`
✅ Cập nhật `module-info.java` để cho phép đọc từ unnamed modules
✅ Thêm compiler argument: `--add-reads com.example.fucarrentingsystem=ALL-UNNAMED`
✅ Cập nhật JavaFX plugin với runtime options
✅ Project compile và build thành công

### Lý do lỗi hiển thị:

Thư viện `jakarta.mail` không phải là một Java module hợp lệ (non-modular JAR), nên nó chạy trong "unnamed module". IntelliJ IDEA cần reload cache để nhận diện cấu hình mới này.

---

## ✨ Tính năng đã hoàn thành

Sau khi reload Maven, tính năng **Gửi hóa đơn về email** sẽ hoạt động:

- ✅ Khách hàng có thể cập nhật email trong phần Hồ sơ
- ✅ Nút "Gửi hóa đơn" trong bảng Lịch sử thuê xe
- ✅ Email hóa đơn HTML đẹp với đầy đủ thông tin
- ✅ Gửi đến email tài khoản của khách hàng

---

## 🎯 Kiểm tra sau khi reload

Chạy lệnh này để đảm bảo project vẫn build được:

```bash
.\mvnw.cmd clean package -DskipTests
```

Kết quả phải là: **BUILD SUCCESS** ✅

---

## 📧 Cấu hình Email

Nhớ cập nhật thông tin email trong `EmailService.java`:

```java
private static final String SENDER_EMAIL = "your_email@gmail.com";
private static final String SENDER_PASSWORD = "your_app_password";
```

**Lưu ý:** Sử dụng **App Password** của Gmail, không phải mật khẩu thông thường!

---

**Nếu vẫn gặp vấn đề, hãy thử restart lại IntelliJ IDEA hoàn toàn.**

