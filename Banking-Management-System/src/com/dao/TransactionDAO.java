package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.entity.Transaction;

public class TransactionDAO {
    private Connection connection; // Initialize the connection in your constructor

    // Constructor for initializing the database connection
    public TransactionDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean createTransaction(Transaction transaction) {
        try {
            String insertQuery = "INSERT INTO transactions (date, amount, account_id, teller_id) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setDate(1, new java.sql.Date(transaction.getDate().getTime()));
            preparedStatement.setBigDecimal(2, transaction.getAmount());
            preparedStatement.setInt(3, transaction.getAccount().getId());
            preparedStatement.setInt(4, transaction.getTeller().getId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}