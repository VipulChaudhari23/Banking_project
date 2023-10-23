package com.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.Connection.JdbcConnection;
import com.entity.Employee;
import com.service.BankImp;

public class EmployeeController {
	
	// common object declare of BankImp
	BankImp empbankImp = new BankImp();
	
	public Employee createEmployee() throws SQLException {
	    return empbankImp.addEmployee();
	}
	
	public boolean removeEmployee() throws SQLException {	    
	    return empbankImp.deleteEmployee();
	}
	
	public boolean UpdateEmployeebyID() throws SQLException {	    
	    return empbankImp.updateEmployee();
	}
	
	public Employee getEmployeebyID() throws SQLException {	    
	    return empbankImp.findEmployeeById();
	}

	public boolean setManager() throws SQLException {	    
		return empbankImp.setManager();
	}
}
