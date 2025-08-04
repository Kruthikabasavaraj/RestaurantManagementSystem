package dao.impl;

import config.DBConnection;
import dao.TableReservationDao;
import model.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

public class TableReservationDaoImpl implements TableReservationDao {

    @Override
    public int insertBooking(Booking booking) {
        String sql = "INSERT INTO table_booking (customer_id, booking_time, num_people, created_time, updated_time) VALUES (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) RETURNING id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, booking.getCustomerId());
            stmt.setTimestamp(2, Timestamp.valueOf(booking.getBookingTime()));
            stmt.setInt(3, booking.getNumPeople());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println("Error inserting booking: " + e.getMessage());
        }
        return -1;
    }
    @Override
    public Map<Integer, String> getAllBookings() {
        Map<Integer, String> map = new LinkedHashMap<>();
        String sql = "SELECT b.id, c.name, b.booking_time FROM table_booking b JOIN customer c ON b.customer_id = c.id ORDER BY b.booking_time";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String info = rs.getString("name") + " - " + rs.getTimestamp("booking_time");
                map.put(id, info);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching bookings: " + e.getMessage());
        }

        return map;
    }
}