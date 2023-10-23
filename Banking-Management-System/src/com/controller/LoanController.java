package com.controller;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.Connection.JdbcConnection;
import com.entity.Branch;
import com.entity.Customer;
import com.entity.Loan;
import com.mysql.cj.xdevapi.Statement;

public class LoanController {
	public Loan createLoan(Customer customer, Branch branch, Date startingDate, Date dueDate, BigDecimal amount) throws SQLException {
	    // Database Connection
	    JdbcConnection connection = new JdbcConnection();
	    Connection con = connection.getconnection();
	    PreparedStatement preparedStatement = null;
	    
	    try {
	        // Insert the loan into the database
	        String insertQuery = "INSERT INTO loan (customer_id, branch_id, starting_date, due_date, amount) VALUES (?, ?, ?, ?, ?)";
	        preparedStatement = con.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS);
	        preparedStatement.setInt(1, customer.getId());
	        preparedStatement.setString(2, branch.getId());
	        preparedStatement.setDate(3, (java.sql.Date) startingDate);
	        preparedStatement.setDate(4, (java.sql.Date) dueDate);
	        preparedStatement.setBigDecimal(5, amount);
	        
	        int rowsAffected = preparedStatement.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            // Retrieve the auto-generated loan ID
	            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                int loanId = generatedKeys.getInt(1);
	                return new Loan();
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
			if (con != null) {
				con.close();
				System.out.println("Closing Connection......");
			} else {
				System.out.println("Cant able to close the connection.....");
			}
		}
	    
	    return null; // Return null on failure
	}

	
	public boolean updateLoan(Loan loan) throws SQLException {
	    // Database Connection
	    JdbcConnection connection = new JdbcConnection();
	    Connection con = connection.getconnection();
	    PreparedStatement preparedStatement = null;
	    
	    try {
	        // Update the loan in the database
	        String updateQuery = "UPDATE loan SET starting_date = ?, due_date = ?, amount = ? WHERE id = ?";
	        preparedStatement = con.prepareStatement(updateQuery);
	        preparedStatement.setDate(1, loan.getStartingDate());
	        preparedStatement.setDate(2, loan.getDueDate());
	        preparedStatement.setBigDecimal(3, loan.getAmount());
	        preparedStatement.setInt(4, loan.getId());
	        
	        int rowsAffected = preparedStatement.executeUpdate();
	        
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
			if (con != null) {
				con.close();
				System.out.println("Closing Connection......");
			} else {
				System.out.println("Cant able to close the connection.....");
			}
		}
	    
	    return false; // Return false on failure
	}

	
	public boolean removeLoan(Loan loan) throws SQLException {
	    // Database Connection
	    JdbcConnection connection = new JdbcConnection();
	    Connection con = connection.getconnection();
	    PreparedStatement preparedStatement = null;
	    
	    try {
	        // Delete the loan from the database
	        String deleteQuery = "DELETE FROM loan WHERE id = ?";
	        preparedStatement = con.prepareStatement(deleteQuery);
	        preparedStatement.setInt(1, loan.getId());
	        
	        int rowsAffected = preparedStatement.executeUpdate();
	        
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
			if (con != null) {
				con.close();
				System.out.println("Closing Connection......");
			} else {
				System.out.println("Cant able to close the connection.....");
			}
		}
	    
	    return false; // Return false on failure
	}

	
	public List<Loan> getLoansOfCustomer(Customer customer) throws SQLException {
	    List<Loan> customerLoans = new ArrayList<>();
	    // Database Connection
	    JdbcConnection connection = new JdbcConnection();
	    Connection con = connection.getconnection();
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    
	    try {
	        // Retrieve loans associated with the customer from the database
	        String selectQuery = "SELECT * FROM loan WHERE customer_id = ?";
	        preparedStatement = con.prepareStatement(selectQuery);
	        preparedStatement.setInt(1, customer.getId());
	        
	        resultSet = preparedStatement.executeQuery();
	        
	        while (resultSet.next()) {
	            int loanId = resultSet.getInt("id");
	            int branchId = resultSet.getInt("branch_id");
	            Date startingDate = resultSet.getDate("starting_date");
	            Date dueDate = resultSet.getDate("due_date");
	            BigDecimal amount = resultSet.getBigDecimal("amount");
	            
	            // Create the Loan and add it to the list
	            Loan loan = new Loan();
	            customerLoans.add(loan);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
			if (con != null) {
				con.close();
				System.out.println("Closing Connection......");
			} else {
				System.out.println("Cant able to close the connection.....");
			}
		}
	    
	    return customerLoans;
	}

	
	public String getLoanDetails(Loan loan) {
	    // Implement logic to format loan details as a string
	    return "Loan ID: " + loan.getId() + ", Starting Date: " + loan.getStartingDate() + ", Due Date: " + loan.getDueDate() + ", Amount: " + loan.getAmount();
	}


}
