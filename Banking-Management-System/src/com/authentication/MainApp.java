package com.authentication;

import java.sql.SQLException;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        // Create an instance of the Admin class
//        Admin admin = new Admin();
//
//        // Call the main method of the Admin class
//        admin.main(args);
    	
        
//        
//        Teller teller = new Teller();
//        teller.main(args);
        
    	Customer customer = new Customer();
    	customer.main(args);
    }
}