package com.example.fucarrentingsystem.service;

import com.example.fucarrentingsystem.entity.CarRental;
import com.example.fucarrentingsystem.entity.Customer;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

public class EmailService {

    // ⚠️ CẤU HÌNH EMAIL - THAY ĐỔI THEO EMAIL CỦA BẠN
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String SENDER_EMAIL = "cuong_2351220227@dau.edu.vn";      // 👈 THAY EMAIL NÀY
    private static final String SENDER_PASSWORD = "hbcp ydyx dear iefo";        // 👈 THAY PASSWORD NÀY

    /**
     * Gửi hóa đơn thuê xe qua email
     */
    public boolean sendRentalInvoice(CarRental rental, Customer customer) {
        try {
            Session session = createSession();
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(SENDER_EMAIL, "FU Car Rental System"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(customer.getEmail()));
            message.setSubject("Hóa đơn thuê xe - FU Car Rental System");

            // Tạo nội dung email đơn giản (text/html)
            String emailContent = buildSimpleInvoiceHtml(rental, customer);
            message.setContent(emailContent, "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("✓ Hóa đơn đã được gửi đến: " + customer.getEmail());
            return true;

        } catch (Exception e) {
            System.err.println("✗ Lỗi khi gửi email: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Tạo nội dung HTML hóa đơn đơn giản
     */
    private String buildSimpleInvoiceHtml(CarRental rental, Customer customer) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        long rentalDays = ChronoUnit.DAYS.between(rental.getPickupDate(), rental.getReturnDate());
        double pricePerDay = rentalDays > 0 ? rental.getRentPrice() / rentalDays : 0;

        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; background-color: #f5f5f5; margin: 0; padding: 20px; }" +
                ".container { max-width: 600px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }" +
                ".header { background-color: #16a085; color: white; padding: 20px; text-align: center; border-radius: 4px; margin-bottom: 30px; }" +
                ".header h1 { margin: 0; font-size: 28px; }" +
                ".section { margin-bottom: 25px; }" +
                ".section-title { font-weight: bold; font-size: 16px; color: #333; border-bottom: 2px solid #16a085; padding-bottom: 10px; margin-bottom: 15px; }" +
                ".info-row { display: flex; justify-content: space-between; padding: 8px 0; border-bottom: 1px solid #eee; }" +
                ".info-label { font-weight: 600; color: #555; min-width: 150px; }" +
                ".info-value { color: #333; text-align: right; flex: 1; }" +
                ".total-section { background-color: #16a085; color: white; padding: 20px; border-radius: 4px; text-align: right; margin-top: 25px; }" +
                ".total-amount { font-size: 24px; font-weight: bold; }" +
                ".footer { text-align: center; margin-top: 30px; padding-top: 20px; border-top: 1px solid #eee; color: #999; font-size: 12px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +

                "<!-- HEADER -->" +
                "<div class='header'>" +
                "<h1>🚗 HÓA ĐƠN THUÊ XE</h1>" +
                "<p style='margin: 10px 0 0 0;'>FU Car Rental System</p>" +
                "</div>" +

                "<!-- THÔNG TIN KHÁCH HÀNG -->" +
                "<div class='section'>" +
                "<div class='section-title'>Thông tin khách hàng</div>" +
                "<div class='info-row'><span class='info-label'>Họ và tên:</span><span class='info-value'>" + customer.getCustomerName() + "</span></div>" +
                "<div class='info-row'><span class='info-label'>Email:</span><span class='info-value'>" + customer.getEmail() + "</span></div>" +
                "<div class='info-row'><span class='info-label'>Số điện thoại:</span><span class='info-value'>" + customer.getMobile() + "</span></div>" +
                "<div class='info-row'><span class='info-label'>CMND/CCCD:</span><span class='info-value'>" + customer.getIdentityCard() + "</span></div>" +
                "</div>" +

                "<!-- THÔNG TIN XE -->" +
                "<div class='section'>" +
                "<div class='section-title'>Thông tin xe thuê</div>" +
                "<div class='info-row'><span class='info-label'>Tên xe:</span><span class='info-value'>" + rental.getCar().getCarName() + "</span></div>" +
                "<div class='info-row'><span class='info-label'>Hãng sản xuất:</span><span class='info-value'>" + rental.getCar().getProducer().getProducerName() + "</span></div>" +
                "<div class='info-row'><span class='info-label'>Năm sản xuất:</span><span class='info-value'>" + rental.getCar().getCarModelYear() + "</span></div>" +
                "<div class='info-row'><span class='info-label'>Màu sắc:</span><span class='info-value'>" + rental.getCar().getColor() + "</span></div>" +
                "<div class='info-row'><span class='info-label'>Số chỗ ngồi:</span><span class='info-value'>" + rental.getCar().getCapacity() + " chỗ</span></div>" +
                "</div>" +

                "<!-- CHI TIẾT THUÊ XE -->" +
                "<div class='section'>" +
                "<div class='section-title'>Chi tiết thuê xe</div>" +
                "<div class='info-row'><span class='info-label'>Ngày nhận xe:</span><span class='info-value'>" + rental.getPickupDate().format(dateFormatter) + "</span></div>" +
                "<div class='info-row'><span class='info-label'>Ngày trả xe:</span><span class='info-value'>" + rental.getReturnDate().format(dateFormatter) + "</span></div>" +
                "<div class='info-row'><span class='info-label'>Số ngày thuê:</span><span class='info-value'>" + rentalDays + " ngày</span></div>" +
                "<div class='info-row'><span class='info-label'>Giá thuê/ngày:</span><span class='info-value'>" + String.format("%,.0f VND", pricePerDay) + "</span></div>" +
                "<div class='info-row'><span class='info-label'>Trạng thái:</span><span class='info-value'>" + rental.getStatus() + "</span></div>" +
                "</div>" +

                "<!-- TỔNG TIỀN -->" +
                "<div class='total-section'>" +
                "<p style='margin: 0 0 10px 0; font-size: 14px;'>TỔNG CỘNG</p>" +
                "<div class='total-amount'>" + String.format("%,.0f VND", rental.getRentPrice()) + "</div>" +
                "</div>" +

                "<!-- FOOTER -->" +
                "<div class='footer'>" +
                "<p>Cảm ơn quý khách đã sử dụng dịch vụ của chúng tôi!</p>" +
                "<p>📞 Hotline: 1900-xxxx | 📧 support@fucarrental.com</p>" +
                "<p style='font-size: 10px; color: #ccc; margin-top: 15px;'>Email này được tạo tự động từ hệ thống. Vui lòng không trả lời email này.</p>" +
                "</div>" +

                "</div>" +
                "</body>" +
                "</html>";
    }

    /**
     * Tạo session SMTP
     */
    private Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.ssl.trust", SMTP_HOST);

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });
    }
}

