package dao.impl;

import config.DBConnection;
import dao.MenuDao;
import model.MenuItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDaoImpl implements MenuDao {

    @Override
    public List<MenuItem> getAllMenuItems() {
        List<MenuItem> list = new ArrayList<>();
        String sql = "SELECT id, name, price FROM menu";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                MenuItem item = new MenuItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price")
                );
                list.add(item);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching menu: " + e.getMessage());
        }

        return list;
    }

    @Override
    public MenuItem getMenuItemById(int id) {
        String sql = "SELECT * FROM menu WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new MenuItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error fetching menu item: " + e.getMessage());
        }

        return null;
    }

    @Override
    public void insertMenuItem(MenuItem item) {
        String sql = "INSERT INTO menu (name, price, created_time, updated_time) VALUES (?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getPrice());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error inserting menu item: " + e.getMessage());
        }
    }

    @Override
    public void updateMenuItemPrice(int id, double newPrice) {
        String sql = "UPDATE menu SET price = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, newPrice);
            stmt.setInt(2, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error updating menu price: " + e.getMessage());
        }
    }
}