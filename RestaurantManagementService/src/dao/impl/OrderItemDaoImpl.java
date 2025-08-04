package dao.impl;

import config.DBConnection;
import dao.OrderItemDao;
import model.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDaoImpl implements OrderItemDao {

    @Override
    public void insertOrderItem(OrderItem item) {
        String sql = "INSERT INTO order_items (order_id, menu_id, quantity) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, item.getOrderId());
            stmt.setInt(2, item.getMenuId());
            stmt.setInt(3, item.getQuantity());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error inserting order item: " + e.getMessage());
        }
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> list = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE order_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderItem item = new OrderItem(
                        rs.getInt("id"),
                        rs.getInt("order_id"),
                        rs.getInt("menu_id"),
                        rs.getInt("quantity")
                );
                list.add(item);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching order items: " + e.getMessage());
        }

        return list;
    }
}