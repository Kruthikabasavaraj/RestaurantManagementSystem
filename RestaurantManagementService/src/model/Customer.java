package model;

import java.time.LocalDateTime;

public class Customer {
    private int id;
    private String name;
    private String phone;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    public Customer() {}

    public Customer(int id, String name, String phone, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public Customer(String name, String phone, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.name = name;
        this.phone = phone;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public Customer(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public Customer(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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