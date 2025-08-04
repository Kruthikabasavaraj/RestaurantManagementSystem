package model;

import java.time.LocalDateTime;

public class Booking {
    private int id;
    private int customerId;
    private LocalDateTime bookingTime;
    private int numPeople;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    public Booking() {}

    public Booking(int id, int customerId, LocalDateTime bookingTime, int numPeople, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.id = id;
        this.customerId = customerId;
        this.bookingTime = bookingTime;
        this.numPeople = numPeople;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public Booking(int customerId, LocalDateTime bookingTime, int numPeople, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.customerId = customerId;
        this.bookingTime = bookingTime;
        this.numPeople = numPeople;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public Booking(int id, int customerId, LocalDateTime bookingTime, int numPeople) {
        this.id = id;
        this.customerId = customerId;
        this.bookingTime = bookingTime;
        this.numPeople = numPeople;
    }

    public Booking(int customerId, LocalDateTime bookingTime, int numPeople) {
        this.customerId = customerId;
        this.bookingTime = bookingTime;
        this.numPeople = numPeople;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }
}