package dao;

import model.MenuItem;
import java.util.List;

public interface MenuDao {
    List<MenuItem> getAllMenuItems();
    MenuItem getMenuItemById(int id);
    void insertMenuItem(MenuItem item);
    void updateMenuItemPrice(int id, double newPrice);
}