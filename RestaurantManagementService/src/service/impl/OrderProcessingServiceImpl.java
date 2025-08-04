package service.impl;

import dao.TableReservationDao;
import dao.MenuDao;
import dao.OrderDao;
import dao.OrderItemDao;
import dao.impl.TableReservationDaoImpl;
import dao.impl.MenuDaoImpl;
import dao.impl.OrderDaoImpl;
import dao.impl.OrderItemDaoImpl;
import model.MenuItem;
import model.Order;
import model.OrderItem;
import service.FoodOrderService;

import java.util.*;

public class OrderProcessingServiceImpl implements FoodOrderService {
    private final MenuDao menuRepository = new MenuDaoImpl();
    private final OrderDao orderRepository = new OrderDaoImpl();
    private final OrderItemDao orderItemRepository = new OrderItemDaoImpl();
    private final TableReservationDao reservationRepository = new TableReservationDaoImpl();

    @Override
    public void takeOrder(Scanner scanner) {
        try {
            System.out.println("\n=== New Order Entry ===");

            // Display current reservations
            var reservations = reservationRepository.getAllBookings();
            if (reservations.isEmpty()) {
                System.out.println("No reservations found at the moment.");
                return;
            }

            System.out.println("Current Reservations:");
            reservations.forEach((id, details) -> System.out.println(id + ": " + details));

            int reservationId;
            while (true) {
                System.out.print("Enter Reservation ID: ");
                String input = scanner.nextLine();
                try {
                    reservationId = Integer.parseInt(input);
                    if (!reservations.containsKey(reservationId)) {
                        System.out.println("Invalid Reservation ID. Please try again.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid numeric Reservation ID.");
                }
            }

            // Display menu options
            List<MenuItem> menuList = menuRepository.getAllMenuItems();
            if (menuList.isEmpty()) {
                System.out.println("Menu is currently unavailable.");
                return;
            }

            Map<Integer, MenuItem> menuMap = new HashMap<>();
            System.out.println("Menu Options:");
            for (MenuItem menuItem : menuList) {
                menuMap.put(menuItem.getId(), menuItem);
                System.out.println(menuItem.getId() + ": " + menuItem.getName() + " (₹" + menuItem.getPrice() + ")");
            }

            List<OrderItem> itemsToOrder = new ArrayList<>();
            while (true) {
                System.out.print("List item IDs to order (comma-separated): ");
                String[] selectedIds = scanner.nextLine().split(",");
                boolean valid = true;
                for (String idStr : selectedIds) {
                    try {
                        int menuId = Integer.parseInt(idStr.trim());
                        if (!menuMap.containsKey(menuId)) {
                            System.out.println("Menu item ID " + menuId + " does not exist.");
                            valid = false;
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid menu item ID: " + idStr.trim());
                        valid = false;
                        break;
                    }
                }
                if (!valid) continue;

                for (String idStr : selectedIds) {
                    int menuId = Integer.parseInt(idStr.trim());
                    int qty;
                    while (true) {
                        System.out.print("Quantity for item " + menuId + ": ");
                        String qtyStr = scanner.nextLine();
                        try {
                            qty = Integer.parseInt(qtyStr);
                            if (qty <= 0) {
                                System.out.println("Quantity must be a positive integer.");
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid quantity.");
                        }
                    }
                    itemsToOrder.add(new OrderItem(0, menuId, qty));
                }
                break;
            }

            String staffName;
            while (true) {
                System.out.print("Waiter Name: ");
                staffName = scanner.nextLine();
                if (staffName == null || staffName.trim().isEmpty()) {
                    System.out.println("Waiter name cannot be empty.");
                } else {
                    break;
                }
            }

            Order newOrder = new Order(reservationId, staffName, "Pending");
            int newOrderId = orderRepository.insertOrder(newOrder);

            for (OrderItem orderItem : itemsToOrder) {
                orderItem.setOrderId(newOrderId);
                orderItemRepository.insertOrderItem(orderItem);
            }

            System.out.println("Order registered successfully! Your Order ID: " + newOrderId);

        } catch (Exception ex) {
            System.out.println("Order process failed: " + ex.getMessage());
        }
    }
}