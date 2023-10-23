package com.project.org;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

import com.authentication.Admin;
import com.authentication.Customer;
import com.authentication.Teller;

public class BankingManagement {
    public static void main(String[] args) throws ParseException, SQLException {
        try {
            Scanner scanner = new Scanner(System.in);
            int mainChoice;
            boolean mainMenuRunning = true;

            while (mainMenuRunning) {
                System.out.println("Select User Type:");
                System.out.println("1. Admin");
                System.out.println("2. Teller");
                System.out.println("3. Customer");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");

                mainChoice = scanner.nextInt();

                switch (mainChoice) {
                    case 1:
                        // Admin
                        System.out.println("Admin Menu");
                        mainMenuRunning = Admin.main(args);
                        break;

                    case 2:
                        // Teller
                        System.out.println("Teller Menu");
                        mainMenuRunning = Teller.main(args);
                        break;

                    case 3:
                        // Customer
                        System.out.println("Customer Menu");
                        mainMenuRunning = Customer.main(args);
                        break;

                    case 4:
                        System.out.println("Exiting the application. Goodbye!");
                        mainMenuRunning = false; // Exit the main menu
                        break;

                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
        } catch (Exception e) {
            // Handle exceptions
        }
    }
}
