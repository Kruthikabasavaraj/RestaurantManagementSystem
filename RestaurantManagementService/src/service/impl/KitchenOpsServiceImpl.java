package service.impl;

import dao.OrderDao;
import dao.impl.OrderDaoImpl;
import model.Order;
import service.KitchenOpsService;

import java.util.List;
import java.util.Scanner;

public class KitchenOpsServiceImpl implements KitchenOpsService {
    private final OrderDao orderRepository = new OrderDaoImpl();

    @Override
    public void viewAndPrepareOrders(Scanner scanner) {
        System.out.println("\n=== Kitchen Task List ===");

        List<Order> ordersToPrepare = orderRepository.getPendingOrders();
        if (ordersToPrepare.isEmpty()) {
            System.out.println("All orders are up to date. No pending tasks.");
            return;
        }

        System.out.println("Orders Awaiting Preparation:");
        for (Order order : ordersToPrepare) {
            System.out.println("ID: " + order.getId() +
                    " | Table: " + order.getBookingId() +
                    " | Assigned Waiter: " + order.getWaiterName() +
                    " | Placed At: " + order.getCreatedAt());
        }

        int selectedOrderId = -1;
        while (true) {
            System.out.print("Input the Order ID to set as ready: ");
            String input = scanner.nextLine().trim();
            try {
                selectedOrderId = Integer.parseInt(input);
                int finalSelectedOrderId = selectedOrderId;
                boolean exists = ordersToPrepare.stream().anyMatch(o -> o.getId() == finalSelectedOrderId);
                if (!exists) {
                    System.out.println("Invalid Order ID. Please select a valid ID from the list.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid Order ID.");
            }
        }

        boolean updated = orderRepository.updateOrderStatus(selectedOrderId, "Prepared");
        if (updated) {
            System.out.println("Order #" + selectedOrderId + " is now marked as ready for serving.");
        } else {
            System.out.println("Unable to update the order status. Please check the Order ID.");
        }
    }
}