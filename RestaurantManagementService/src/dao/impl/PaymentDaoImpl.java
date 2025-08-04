package dao.impl;

import config.DBConnection;
import dao.PaymentDao;
import model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class PaymentDaoImpl implements PaymentDao {

    @Override
    public void insertPayment(Payment payment) {
        String sql = "INSERT INTO payments (order_id, amount, payment_method, payment_time) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, payment.getOrderId());
            stmt.setDouble(2, payment.getAmount());
            stmt.setString(3, payment.getPaymentMethod());
            stmt.setTimestamp(4, Timestamp.valueOf(payment.getPaymentTime()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error recording payment: " + e.getMessage());
        }
    }

    @Override
    public double getTodaySales() {
        String sql = "SELECT SUM(amount) FROM payments WHERE DATE(payment_time) = CURRENT_DATE";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble(1);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching sales report: " + e.getMessage());
        }

        return 0.0;
    }
}