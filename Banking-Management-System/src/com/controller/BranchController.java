package com.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.Connection.JdbcConnection;
import com.entity.Branch;
import com.entity.Employee;
import com.service.BankImp;

public class BranchController {

//  ------------------------------------ Branch Operation Start ------------------------------------------------------	
	
	// common object declare of BankImp
	BankImp barnchbankImp = new BankImp();
	
	// Create Branch
	public Branch createBranch() throws SQLException {
		return barnchbankImp.addBranch();
	};

	// Update branch using the branch ID
	public boolean updateBranch() throws SQLException {
		return barnchbankImp.updateBranch();
	}
	
	// delete the branch using the id
	public boolean removeBranch() throws SQLException {
	    return barnchbankImp.deleteBranch();
	}
	
	// print branch using id
	public Branch getBranchDetails() throws SQLException {
	    return barnchbankImp.BranchDetails();
	}
	
//  ------------------------------------ Branch Operation End ------------------------------------------------------	
	
	public List<Employee> getBranchEmployees() {
		return null;
	};

}
