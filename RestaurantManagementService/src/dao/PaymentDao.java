package dao;

import model.Payment;

public interface PaymentDao {
    void insertPayment(Payment payment);
    double getTodaySales();
}