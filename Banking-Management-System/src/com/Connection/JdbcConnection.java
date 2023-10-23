package com.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection {
	public Connection getconnection() throws SQLException {

		String url = "jdbc:mysql://localhost:3306/bank_management";
		String username = "root";
		String password = "1234";
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, username, password);
			if (con != null) {
				System.out.println("Connection is successfuly...");
			} else {
				System.out.println("Not able to connect.....");
			}
		} catch (Exception e) {
			System.out.println("ERROR" + e.getMessage());
		} 
		return con;
	}
}