package service.impl;

import dao.TableReservationDao;
import dao.CustomerDao;
import dao.impl.TableReservationDaoImpl;
import dao.impl.CustomerDaoImpl;
import model.Booking;
import model.Customer;
import service.TableReservationService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ReservationHandlerServiceImpl implements TableReservationService {
    private final CustomerDao customerRepository = new CustomerDaoImpl();
    private final TableReservationDao reservationRepository = new TableReservationDaoImpl();

    @Override
    public void bookTable(Scanner scanner) {
        try {
            System.out.println("\n=== Reserve a Table ===");

            String customerName;
            while (true) {
                System.out.print("Customer Name: ");
                customerName = scanner.nextLine().trim();
                if (customerName.isEmpty()) {
                    System.out.println("Customer name cannot be empty.");
                } else {
                    break;
                }
            }

            String contactNumber;
            while (true) {
                System.out.print("Contact Number: ");
                contactNumber = scanner.nextLine().trim();
                if (contactNumber.isEmpty() || !contactNumber.matches("\\d{10}")) {
                    System.out.println("Contact number must be 10 digits.");
                } else {
                    break;
                }
            }

            int partySize;
            while (true) {
                System.out.print("Party Size: ");
                String partySizeInput = scanner.nextLine().trim();
                try {
                    partySize = Integer.parseInt(partySizeInput);
                    if (partySize <= 0) {
                        System.out.println("Party size must be a positive number.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number for party size.");
                }
            }

            LocalDateTime reservationTime;
            while (true) {
                System.out.print("Reservation Date & Time (yyyy-MM-dd HH:mm): ");
                String dateTimeInput = scanner.nextLine().trim();
                try {
                    reservationTime = LocalDateTime.parse(dateTimeInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    if (reservationTime.isBefore(LocalDateTime.now())) {
                        System.out.println("Reservation time cannot be in the past.");
                        continue;
                    }
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date/time format. Please use yyyy-MM-dd HH:mm.");
                }
            }

            // Register customer
            Customer newCustomer = new Customer(customerName, contactNumber);
            int newCustomerId = customerRepository.insertCustomerRow(newCustomer);

            // Register reservation
            Booking newBooking = new Booking(newCustomerId, reservationTime, partySize);
            int newBookingId = reservationRepository.insertBooking(newBooking);

            System.out.println("Reservation confirmed! Your reference ID: " + newBookingId);
        } catch (Exception ex) {
            System.out.println("Reservation failed: " + ex.getMessage());
        }
    }
}