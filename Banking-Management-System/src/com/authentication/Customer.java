package com.authentication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.mindrot.jbcrypt.BCrypt;

import com.Connection.JdbcConnection;
import com.service.BankImp;

public class Customer {
    public static boolean main(String[] args) throws SQLException {
        JdbcConnection connection = new JdbcConnection();
        Connection con = connection.getconnection();
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Enter Customer login ID/Username: ");
            String login = sc.next();
            System.out.print("Enter Customer password: ");
            String password = sc.next();

            if (validateCustomerLogin(con, login, password)) {
                System.out.println("Customer authentication successful.");
                customerMenu(sc);
            } else {
                System.out.println("Invalid credentials. Authentication failed.");
            }
        } finally {
            sc.close(); // Close the Scanner in the finally block
        }
		return false;
    }

    private static boolean validateCustomerLogin(Connection connection, String login, String password) throws SQLException {
        String query = "SELECT passhash FROM customer WHERE login = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String storedHashedPassword = resultSet.getString("passhash");
                return BCrypt.checkpw(password, storedHashedPassword);
            }
        }
        return false;
    }

    private static void customerMenu(Scanner sc) throws SQLException {
        BankImp bankImp = new BankImp();
    	JdbcConnection connection = new JdbcConnection();
		Connection con = connection.getconnection();
        
        try {
        	boolean flag3 = true;

            while (flag3) {
                System.out.println("\n1. Update personal details\n2. Change password\n3. View balance\n4. View Customer Details\n5. View personal history of transactions\n6. Transfer money\n7. Withdraw\n8. Deposit\n9. Exit");
                int choice2 = sc.nextInt();

                switch (choice2) {
                    case 1:
                        // Update personal details
                        bankImp.updateCustomerById();
                        break;

                    case 2:
                        // Change password
                        bankImp.changePasswordCustomer();
                        break;

                    case 3:
                        // View balance
                        // Implement logic to view customer balance
                    	bankImp.viewBalanceByCustomerId();
                        break;
                    case 4:
                        // View customer details
                        // Implement logic View customer details
                    	bankImp.viewCustomerDetails();
                        break;
                    case 5:
                        // View personal history of transactions
                        // Implement logic to view transaction history
                    	bankImp.viewTransactionHistory();
                        break;

                    case 6:
                        // Transfer money
                        // Implement logic for money transfer
                    	bankImp.transferMoney();
                        break;

                    case 7:
                        // Withdraw
                        // Implement logic for withdrawal
                    	bankImp.withdraw();
                        break;

                    case 8:
                        // Deposit
                        // Implement logic for deposit
                    	bankImp.addDeposit();
                        break;

                    case 9:
                        // Exit
                        flag3 = false;
                        break;

                    default:
                        System.out.println("Enter a valid choice.");
                }
            }
		} catch (Exception e) {
			// TODO: handle exception
        	
		} finally {
			if (con != null) {
				con.close();
				System.out.println("Closing Connection......");
			} else {
				System.out.println("Can't able to close the connection.");
			}
		}
        
    }
}
