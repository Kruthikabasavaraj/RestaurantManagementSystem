import service.AdminService;
import service.BillingService;
import service.TableReservationService;
import service.KitchenOpsService;
import service.FoodOrderService;
import service.impl.AdminServiceImpl;
import service.impl.BillingServiceImpl;
import service.impl.ReservationHandlerServiceImpl;
import service.impl.KitchenOpsServiceImpl;
import service.impl.OrderProcessingServiceImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        TableReservationService tableBooking = new ReservationHandlerServiceImpl();
        FoodOrderService orderHandler = new OrderProcessingServiceImpl();
        KitchenOpsService kitchenOps = new KitchenOpsServiceImpl();
        BillingService billGenerator = new BillingServiceImpl();
        AdminService adminPanel = new AdminServiceImpl();

        boolean running = true;
        while (running) {
            displayMainMenu();
            String option = input.nextLine();

            switch (option) {
                case "1":
                    tableBooking.bookTable(input);
                    break;
                case "2":
                    orderHandler.takeOrder(input);
                    break;
                case "3":
                    kitchenOps.viewAndPrepareOrders(input);
                    break;
                case "4":
                    billGenerator.createInvoice(input);
                    break;
                case "5":
                    adminPanel.adminMenu(input);
                    break;
                case "0":
                    System.out.println("Exiting Restaurant Management. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Please enter a valid option.");
            }
        }
        input.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n********* Restaurant Management *********");
        System.out.println("Choose your role:");
        System.out.println("1. Book a Table (Customer)");
        System.out.println("2. Place an Order (Waiter)");
        System.out.println("3. Kitchen Operations");
        System.out.println("4. Billing (Manager)");
        System.out.println("5. Administration");
        System.out.println("0. Quit");
        System.out.print("Your selection: ");
    }
}
