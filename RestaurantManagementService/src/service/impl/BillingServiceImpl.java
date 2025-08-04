package service.impl;

import dao.*;
import dao.impl.MenuDaoImpl;
import dao.impl.OrderDaoImpl;
import dao.impl.OrderItemDaoImpl;
import dao.impl.PaymentDaoImpl;
import model.*;
import service.BillingService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class BillingServiceImpl implements BillingService {
    private final OrderDao orderRepository = new OrderDaoImpl();
    private final OrderItemDao orderItemRepository = new OrderItemDaoImpl();
    private final MenuDao menuRepository = new MenuDaoImpl();
    private final PaymentDao paymentRepository = new PaymentDaoImpl();

    @Override
    public void createInvoice(Scanner scanner) {
        System.out.println("\n--- Invoice Generation & Payment ---");

        List<Order> readyOrders = orderRepository.getPreparedOrders();
        if (readyOrders.isEmpty()) {
            System.out.println("No ready orders available for billing.");
            return;
        }

        System.out.println("Ready Orders List:");
        for (Order order : readyOrders) {
            System.out.println("Order #" + order.getId() +
                    " | Booking: " + order.getBookingId() +
                    " | Served by: " + order.getWaiterName());
        }

        int selectedOrderId = -1;
        while (true) {
            System.out.print("Select Order ID for invoice: ");
            String input = scanner.nextLine().trim();
            try {
                selectedOrderId = Integer.parseInt(input);
                int finalSelectedOrderId = selectedOrderId;
                boolean exists = readyOrders.stream().anyMatch(o -> o.getId() == finalSelectedOrderId);
                if (!exists) {
                    System.out.println("Invalid Order ID. Please select a valid ID from the list.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid Order ID.");
            }
        }

        List<OrderItem> orderItems = orderItemRepository.getOrderItemsByOrderId(selectedOrderId);
        double grandTotal = displayInvoiceDetails(orderItems);

        System.out.println("Amount Due: ₹" + grandTotal);

        String paymentType = "";
        while (true) {
            System.out.print("Payment type (Cash/Card/UPI): ");
            paymentType = scanner.nextLine().trim();
            if (paymentType.equalsIgnoreCase("Cash") ||
                    paymentType.equalsIgnoreCase("Card") ||
                    paymentType.equalsIgnoreCase("UPI")) {
                break;
            } else {
                System.out.println("Invalid payment type. Please enter Cash, Card, or UPI.");
            }
        }

        Payment payment = new Payment(selectedOrderId, grandTotal, paymentType);
        payment.setPaymentTime(LocalDateTime.now());
        paymentRepository.insertPayment(payment);

        orderRepository.updateOrderStatus(selectedOrderId, "Paid");

        System.out.println("Payment successful. Invoice completed.");
    }

    private double displayInvoiceDetails(List<OrderItem> items) {
        double sum = 0.0;
        System.out.println("\n--- Invoice Details ---");
        for (OrderItem item : items) {
            MenuItem menuItem = menuRepository.getMenuItemById(item.getMenuId());
            double lineTotal = menuItem.getPrice() * item.getQuantity();
            System.out.println(menuItem.getName() + " x " + item.getQuantity() + " = ₹" + lineTotal);
            sum += lineTotal;
        }
        return sum;
    }
}