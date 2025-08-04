package model;

import java.time.LocalDateTime;

public class MenuItem {
    private int id;
    private String name;
    private double price;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    public MenuItem() {}

    public MenuItem(int id, String name, double price, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public MenuItem(String name, double price, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.name = name;
        this.price = price;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public MenuItem(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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