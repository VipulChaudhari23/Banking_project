package com.authentication;

import java.sql.SQLException;
import java.util.Scanner;

import com.service.BankImp;

public class Admin {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    

    public static boolean main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Admin Login username/ ID: ");
        String username = scanner.nextLine();
        System.out.print("Enter Admin password: ");
        String password = scanner.nextLine();

        boolean isAuthenticated = authenticate(username, password);

        if (isAuthenticated) {
            System.out.println("Admin authentication successful.");
            adminMenu(scanner);
        } else {
            System.out.println("Invalid credentials. Authentication failed.");
        }

        scanner.close();
		return isAuthenticated;
    }

    private static boolean authenticate(String username, String password) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }

    private static void adminMenu(Scanner scanner) throws SQLException {
    	BankImp bankImp =  new BankImp();
        boolean flag1 = true;

        while (flag1) {
            System.out.println("\n1.View Manager details\n2.View Customer details\n3.Add Bank branch Details\n4.Update Bank branch Details\n5.Add Manager Details\n6.Add Employee\n7.Delete Employee\n8.Exit");
            int choice2 = scanner.nextInt();
            scanner.nextLine();

            switch (choice2) {
                case 1: {
                    // View Manager details
                	bankImp.findAllmanager();
                }
                break;
                case 2: {
                    // View Customer details
                	bankImp.findAllCustomers();
                }
                break;
                case 3: {
                    // Add Bank branch Details
                	bankImp.addBranch();
                }
                break;
                case 4: {
                    // Update Bank branch Details
                	bankImp.updateBranch();
                }
                break;
                case 5: {
                    // Add Manager details
                	bankImp.setManager();
                }
                break;
                case 6: {
                	// Add Employee
                	bankImp.addEmployee();
                }
                break;
                case 7: {
                    // Delete Employee
                	bankImp.deleteEmployee();
                }
                break;
                case 8: {
                    // Exit
                    flag1 = false;
                }
                break;
                default: {
                    System.out.println("Enter a valid choice.");
                }
            }
        }
    }
}
