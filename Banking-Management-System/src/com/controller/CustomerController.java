package com.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.Connection.JdbcConnection;
import com.entity.Account;
import com.entity.Customer;
import com.service.BankImp;

public class CustomerController {
	// common object declare of BankImp
	BankImp custbankImp = new BankImp();
	
//  ------------------------------------ Customer Operation Start ------------------------------------------------------	

	// Register New Bank Customer using createCustomer method
	public Customer createCustomer() throws ParseException, SQLException {
		return custbankImp.add();
	}

	// Delete Customer
	public boolean removeCustomer() throws SQLException {
		return custbankImp.deleteCustomer();
	}
	
	// print all records from the customer table
	public List<Customer> showRecords() throws SQLException{
		return custbankImp.findAllCustomers();  
	}
	
	// print all records from the customer table using Id
	public Customer getCustomerbyID() throws SQLException {
		return custbankImp.findCustomerByID();	
	}

	// print all records from the customer table using Name
	public List<Customer> getCustomerbyName() throws SQLException {
		return custbankImp.findCustomersByName();	
	}
	
	// update customer using Id
	public boolean Updatecustomer() throws SQLException {
		return custbankImp.updateCustomerById();
	}
	
//  ------------------------------------ Customer Operation End ------------------------------------------------------	

	
	public Customer getCustomerOfAccount(Account account) throws SQLException {
		Customer customer = null;

		JdbcConnection connection = new JdbcConnection();
		Connection con = connection.getconnection();
		try {

			// Assuming there's a relationship between the Account and Customer tables,
			// replace "account_id" with the actual column name in the Customer table
			String selectQuery = "SELECT * FROM customer WHERE account_id = ?";
			PreparedStatement preparedStatement = con.prepareStatement(selectQuery);
			preparedStatement.setInt(1, account.getId()); // Assuming you can get the Account ID

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				customer = new Customer();
				customer.setLogin(resultSet.getString("login"));
				customer.setPasshash(resultSet.getString("passhash"));
				customer.setName(resultSet.getString("name"));
				customer.setPhone(resultSet.getString("phone"));
				customer.setEmail(resultSet.getString("email"));
				customer.setRegistrationDate(resultSet.getDate("registrationDate"));
			}

			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (con != null) {
				con.close();
				System.out.println("Closing Connection......");
			} else {
				System.out.println("Cant able to close the connection.....");
			}
		
		}
		return customer;
	}

}
