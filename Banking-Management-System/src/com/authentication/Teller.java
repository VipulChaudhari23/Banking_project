package com.authentication;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.Connection.JdbcConnection;
import com.service.BankImp;

public class Teller {
    public static boolean main(String[] args) throws SQLException {
    	
        // Your database connection information
//        String dbUrl = "jdbc:mysql://localhost:3306/bank_management";
//        String dbUser = "root";
//        String dbPassword = "1234";
    	        
		JdbcConnection connection = new JdbcConnection();
		Connection con = connection.getconnection();
        Scanner sc = new Scanner(System.in);

		System.out.print("Enter Teller login Id/Username: ");
        String login = sc.next();
        System.out.print("Enter Teller password: ");
        String password = sc.next();

        if (validateTellerLogin(con, login, password)) {
            System.out.println("Teller authentication successful.");
            TellerMenu(sc);
            // Proceed with teller functionality
        } else {
            System.out.println("Invalid credentials. Authentication failed.");
        }
		return false;
    }

        // Establish a database connection
        private static void TellerMenu(Scanner sc) throws SQLException {
        	BankImp bankImp =  new BankImp();
        	JdbcConnection connection = new JdbcConnection();
    		Connection con = connection.getconnection();
        
        try {
//            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        	

            boolean flag2 = true;

            while (flag2) {
                System.out.println("\n1.Change password\n2.Register new Bank customer\n3.View Customer Details\n4.Update Customer\n5.Remove Customer\n6.Update Employee\n7. Find Customer By Id\n8.Exit");
                int choice2 = sc.nextInt();

                switch (choice2) {
                    case 1:
                        // Change password
                        // Implement password change logic here
                    	bankImp.changePassword();                    	
                        break;

                    case 2:
                        // Register new Bank customer
                        // Implement customer registration logic here
                    	bankImp.addWithAccount();
                        break;

                    case 3:
                        // View Customer Details
                        // Implement customer details viewing logic here
                    	bankImp.findAllCustomers();
                        break;

                    case 4:
                        // Update Customer
                        // Implement Update Customer logic here
                    	bankImp.updateCustomerById();
                        break;
                    case 5:
                        // Remove Customer
                        // Implement Remove Customer logic here
                    	bankImp.deleteCustomer();
                        break;
                    case 6:
                        // Update Employee
                        // Implement Update Employee logic here
                    	bankImp.updateEmployee();
                        break;
                    case 7:
                        // Find Customer by ID
                        // Implement Find Customer by ID logic here
                    	bankImp.findCustomerByID();
                        break;
                    case 8:
                        // Exit
                        flag2 = false;
                        break;

                    default:
                        System.out.println("Enter a valid choice.");
                }
            }
        }
        catch (Exception e) {
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
 
    private static boolean validateTellerLogin(Connection connection, String login, String password) {
        String query = "SELECT * FROM employee WHERE login = ? AND passhash = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Returns true if a matching record is found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}

