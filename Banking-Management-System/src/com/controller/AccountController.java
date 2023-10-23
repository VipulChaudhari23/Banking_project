package com.controller;

import java.math.BigDecimal;
import java.sql.Date; // Import the correct Date class

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.Connection.JdbcConnection;
import com.entity.Account;
import com.entity.Branch;
import com.entity.Customer;
import com.service.BankImp;

public class AccountController {
	BankImp accbankImp = new BankImp(); 

	
	public Account createAccount() throws SQLException {
	    return accbankImp.addAcount();
	}
	
	private Branch getBranchById(String branchId) throws SQLException {
		// TODO Auto-generated method stub
		return accbankImp.BranchDetails();
	}
	
	private Customer getCustomerById(int customerId) throws SQLException {
		// TODO Auto-generated method stub
		return accbankImp.findCustomerByID();
	}


	public boolean updateAccountById() throws SQLException {
		return accbankImp.updateAccount();
	}


	public boolean removeAccount() throws SQLException {
	    return accbankImp.deleteAccount();
	}

	public List<Account> getAccountsOfCustomer(Customer customer) throws SQLException {
		return accbankImp.findAccountsOfCustomer(); // Return an empty list in case of an error.
	}


}
