package com.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.entity.Account;
import com.entity.Branch;
import com.entity.Customer;
import com.entity.Employee;
import com.entity.Loan;
import com.entity.Transaction;

public interface Bank {
	
		// Create
		Customer add() throws ParseException, SQLException;
		Employee addEmployee() throws SQLException;
		Account addAcount() throws SQLException;
		boolean add(Loan loan);
		Branch addBranch() throws SQLException;
		
		boolean addDeposit() throws SQLException;
		
		// Read customer
		Customer findCustomerByID() throws SQLException;
	    List<Customer> findCustomersByName() throws SQLException;
	    
	    List<Customer> findAllCustomers() throws SQLException;    
	    
	    Employee findEmployeeById() throws SQLException;
	    List<Account> findAccountsOfCustomer() throws SQLException;
	    List<Loan>findLoansOfCustomer();
	    
//	    List<Transaction> findTransactionsOfAccount(Account account) throws SQLException;
	    String getTransactionDetails(Transaction transaction);
	    
	    Branch BranchDetails() throws SQLException;
	    boolean setManager() throws SQLException;
	    boolean withdraw() throws SQLException;
	    boolean transfer() throws SQLException;
	    
		
		// Update
	    boolean updateCustomerById() throws SQLException;
	    boolean updateEmployee() throws SQLException;
	    boolean updateAccount() throws SQLException;
		boolean update(Loan loan);
		boolean updateBranch() throws SQLException;
		
		// Delete
		boolean deleteCustomer() throws SQLException;
		boolean deleteEmployee() throws SQLException;
		boolean deleteAccount() throws SQLException;
		boolean delete(Loan loan);
		boolean deleteBranch() throws SQLException;
}
