# ✅ CHECKLIST - Gửi Hóa Đơn Email

## 🔧 Setup Checklist

- [ ] Mở file `EmailService.java`
  - [ ] Dòng 17: Thay `SENDER_EMAIL` = email của bạn (Gmail)
  - [ ] Dòng 18: Thay `SENDER_PASSWORD` = App Password (16 ký tự)

- [ ] Lấy App Password Gmail:
  - [ ] Vào https://myaccount.google.com/security
  - [ ] Bật 2-Step Verification (nếu chưa)
  - [ ] Tìm "App passwords"
  - [ ] Tạo password mới cho Mail
  - [ ] Copy 16 ký tự (bỏ dấu cách)

- [ ] Build project:
  - [ ] Chạy: `.\mvnw.cmd clean compile`
  - [ ] Verify: Build SUCCESS

## 🚀 Runtime Checklist

- [ ] Chạy ứng dụng
- [ ] Đăng nhập khách hàng
- [ ] Vào tab "Lịch sử thuê xe"
- [ ] Thấy nút "Gửi hóa đơn"? ✅
  - [ ] Có → Tiếp tục
  - [ ] Không → Kiểm tra FXML

- [ ] Click nút "Gửi hóa đơn"
- [ ] Xác nhận email
- [ ] Thấy dialog loading? ✅
- [ ] Dialog đóng & thông báo thành công? ✅
- [ ] Kiểm tra email đã nhận? ✅
  - [ ] Check Inbox
  - [ ] Check Spam folder

## 📧 Email Verify

- [ ] Email nhận được có header "🚗 HÓA ĐƠN THUÊ XE"? ✅
- [ ] Có thông tin khách hàng? ✅
  - [ ] Tên
  - [ ] Email
  - [ ] SĐT
  - [ ] CMND

- [ ] Có thông tin xe? ✅
  - [ ] Tên xe
  - [ ] Hãng sản xuất
  - [ ] Năm sản xuất
  - [ ] Màu sắc
  - [ ] Số chỗ

- [ ] Có chi tiết thuê xe? ✅
  - [ ] Ngày nhận
  - [ ] Ngày trả
  - [ ] Số ngày
  - [ ] Giá/ngày

- [ ] Có tổng tiền? ✅

## 🐛 Troubleshooting Checklist

Nếu không thành công:

### Email không gửi được
- [ ] Kiểm tra Internet connection
- [ ] Kiểm tra `SENDER_EMAIL` đúng chưa?
- [ ] Kiểm tra `SENDER_PASSWORD` đúng chưa?
- [ ] Xác nhận dùng App Password (không phải mật khẩu Gmail)?
- [ ] Xác nhận 2-Step Verification đã bật?
- [ ] Kiểm tra firewall/antivirus có chặn port 587 không?

### Email không đến
- [ ] Kiểm tra thư mục Spam
- [ ] Kiểm tra email khách hàng đúng chưa?
- [ ] Xác nhận email trong profile khách hàng?
- [ ] Thử gửi lại sau vài phút

### Lỗi IDE
- [ ] Lỗi compile? Chạy `.\mvnw.cmd clean compile`
- [ ] IDE vẫn báo lỗi? Reload Maven project
- [ ] Vẫn lỗi? Restart IDE

### Nút không xuất hiện
- [ ] Kiểm tra FXML có cột `rentalActionColumn`?
- [ ] Kiểm tra Controller có field `rentalActionColumn`?
- [ ] Kiểm tra `setupRentalHistoryTable()` có setup nút không?

---

## ✨ Features

- [x] Nút "Gửi hóa đơn" trong bảng lịch sử
- [x] Email template HTML đẹp mắt
- [x] Lấy dữ liệu từ database
- [x] Validate email khách hàng
- [x] Dialog xác nhận trước gửi
- [x] Loading indicator
- [x] Error handling
- [x] Background thread (không làm đơ UI)

---

## 📚 Files

| File | Status | Ghi chú |
|------|--------|--------|
| EmailService.java | 🆕 Tạo mới | Service gửi email |
| CustomerDashboardController.java | ✏️ Sửa | Thêm handler + setup |
| customer-dashboard.fxml | ✏️ Sửa | Thêm cột action |
| pom.xml | ✅ Sẵn có | jakarta.mail dependency |
| module-info.java | ✅ Sẵn có | requires jakarta.mail |

---

## 🎯 Success Criteria

✅ Project build successfully
✅ Nút "Gửi hóa đơn" xuất hiện
✅ Click nút → Dialog xác nhận
✅ Xác nhận → Loading indicator
✅ Email gửi → Thông báo success
✅ Khách hàng nhận email đầy đủ thông tin

---

## 📞 Support

Nếu gặp vấn đề:
1. Kiểm tra checklist phía trên
2. Xem `SEND_INVOICE_GUIDE.md`
3. Xem `COMPLETED_SUMMARY.md`
4. Kiểm tra console log của application

---

**Status: ✅ READY TO USE**

Cần làm gì tiếp?
1. Cấu hình email (thay SENDER_EMAIL & SENDER_PASSWORD)
2. Build & Run
3. Test gửi email

Thế là xong! 🎉

