# 🚀 QUICK START - Gửi Hóa Đơn Email

## ⚡ 5 Phút Setup

### Bước 1: Lấy Gmail App Password (2 phút)

1. Vào: https://myaccount.google.com/security
2. **"2-Step Verification"** → Bật (nếu chưa)
3. **"App passwords"** → Tìm mục này
4. **App**: Mail | **Device**: Windows Computer
5. **Generate** → Copy 16 ký tự

Ví dụ: `xxxx xxxx xxxx xxxx`

### Bước 2: Cấu hình Code (1 phút)

Mở file: `EmailService.java`

Dòng 17-18, thay:
```java
private static final String SENDER_EMAIL = "your-gmail@gmail.com";  // ← EMAIL CỦA BẠN
private static final String SENDER_PASSWORD = "xxxxxxxxxxxx";        // ← 16 KÝ TỰ (BỎ DẤU CÁCH)
```

### Bước 3: Build (1 phút)

```bash
cd C:\Users\cuongJS\IdeaProjects\fucarrentingsystem
.\mvnw.cmd clean compile
```

✅ Success! Bạn đã setup xong

### Bước 4: Test (1 phút)

1. Chạy app
2. Đăng nhập khách hàng
3. Tab "Lịch sử thuê xe"
4. Click **"Gửi hóa đơn"**
5. ✅ Check email!

---

## 🎯 Kết quả

Khách hàng nhận được email:

```
┌────────────────────────────────┐
│  🚗 HÓA ĐƠN THUÊ XE           │
├────────────────────────────────┤
│ Khách hàng: [Tên]              │
│ Email: [Email]                 │
│ SĐT: [SĐT]                     │
│                                │
│ Xe: [Tên xe]                   │
│ Hãng: [Hãng]                   │
│ Năm: [Năm]                     │
│ Màu: [Màu]                     │
│ Chỗ: [Số]                      │
│                                │
│ Từ: [Ngày nhận]                │
│ Đến: [Ngày trả]                │
│ Ngày: [Số ngày]                │
│ Giá/ngày: [Giá]                │
│                                │
│ 💰 TỔNG: [TỔNG] VND            │
└────────────────────────────────┘
```

---

## ⚠️ Lưu Ý

❌ **KHÔNG QUÊN**:
- Dùng **App Password** (không phải mật khẩu Gmail thường)
- 2-Step Verification **phải bật**

✅ **SỬ DỤNG**:
- Dùng `System.getenv()` cho production (bảo mật hơn)

---

## 🆘 Lỗi?

### "Không thể gửi email"
- [ ] Internet có kết nối?
- [ ] Email & password đúng?
- [ ] Dùng App Password?
- [ ] 2-Step Verification bật?

### "Email không đến"
- [ ] Check thư mục **Spam**
- [ ] Email trong profile có đúng?

### Lỗi compile
```bash
.\mvnw.cmd clean compile
```

---

## 📚 Tài liệu

- `FINAL_SUMMARY.md` - Tóm tắt đầy đủ
- `SEND_INVOICE_GUIDE.md` - Hướng dẫn chi tiết
- `SETUP_CHECKLIST.md` - Checklist

---

## ✅ Done!

Bạn đã setup xong! Khách hàng có thể bắt đầu gửi hóa đơn ngay! 🎉

**Files**:
- ✅ `EmailService.java` - Đã tạo
- ✅ `CustomerDashboardController.java` - Đã update
- ✅ `customer-dashboard.fxml` - Đã update
- ✅ Build - ✅ SUCCESS

**Tiếp theo**: Cấu hình email & Test! 🚀

