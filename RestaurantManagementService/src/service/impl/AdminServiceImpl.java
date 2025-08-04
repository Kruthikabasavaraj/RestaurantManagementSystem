package service.impl;

import dao.MenuDao;
import dao.PaymentDao;
import dao.impl.MenuDaoImpl;
import dao.impl.PaymentDaoImpl;
import model.MenuItem;
import service.AdminService;

import java.util.List;
import java.util.Scanner;

public class AdminServiceImpl implements AdminService {
    private final MenuDao menuRepository = new MenuDaoImpl();
    private final PaymentDao paymentRepository = new PaymentDaoImpl();

    @Override
    public void adminMenu(Scanner scanner) {
        boolean active = true;
        while (active) {
            showAdminOptions();
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    createMenuItem(scanner);
                    break;
                case "2":
                    modifyMenuItemPrice(scanner);
                    break;
                case "3":
                    printDailySales();
                    break;
                case "0":
                    active = false;
                    break;
                default:
                    System.out.println("Please select a valid option.");
            }
        }
    }

    private void showAdminOptions() {
        System.out.println("\n=== Management Console ===");
        System.out.println("1. Create New Menu Item");
        System.out.println("2. Change Menu Item Price");
        System.out.println("3. Show Today's Revenue");
        System.out.println("0. Return to Main Menu");
        System.out.print("Select an option: ");
    }

    private void createMenuItem(Scanner scanner) {
        System.out.print("Menu item name: ");
        String itemName = scanner.nextLine().trim();
        if (itemName.isEmpty()) {
            System.out.println("Item name cannot be empty.");
            return;
        }

        double itemPrice = 0.0;
        while (true) {
            System.out.print("Set price: ");
            String priceInput = scanner.nextLine().trim();
            try {
                itemPrice = Double.parseDouble(priceInput);
                if (itemPrice < 0) {
                    System.out.println("Price cannot be negative.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid price. Please enter a valid number.");
            }
        }

        MenuItem newItem = new MenuItem(itemName, itemPrice);
        menuRepository.insertMenuItem(newItem);
        System.out.println("Item successfully added to the menu.");
    }

    private void modifyMenuItemPrice(Scanner scanner) {
        List<MenuItem> items = menuRepository.getAllMenuItems();

        if (items.isEmpty()) {
            System.out.println("No items found in the menu.");
            return;
        }

        System.out.println("Current Menu:");
        for (MenuItem item : items) {
            System.out.println(item.getId() + ": " + item.getName() + " (₹" + item.getPrice() + ")");
        }

        int itemId = -1;
        while (true) {
            System.out.print("Enter the ID of the item to update: ");
            String idInput = scanner.nextLine().trim();
            try {
                itemId = Integer.parseInt(idInput);
                int finalItemId = itemId;
                boolean exists = items.stream().anyMatch(i -> i.getId() == finalItemId);
                if (!exists) {
                    System.out.println("Invalid item ID. Please select a valid ID from the menu.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid item ID.");
            }
        }

        double updatedPrice = 0.0;
        while (true) {
            System.out.print("Enter the new price: ");
            String priceInput = scanner.nextLine().trim();
            try {
                updatedPrice = Double.parseDouble(priceInput);
                if (updatedPrice < 0) {
                    System.out.println("Price cannot be negative.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid price. Please enter a valid number.");
            }
        }

        menuRepository.updateMenuItemPrice(itemId, updatedPrice);
        System.out.println("Menu item price updated.");
    }

    private void printDailySales() {
        double sales = paymentRepository.getTodaySales();
        System.out.println("Today's total revenue: ₹" + sales);
    }
}