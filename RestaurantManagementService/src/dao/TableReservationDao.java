package dao;

import model.Booking;

import java.util.Map;

public interface TableReservationDao {
    int insertBooking(Booking booking);

    Map<Integer, String> getAllBookings();
}