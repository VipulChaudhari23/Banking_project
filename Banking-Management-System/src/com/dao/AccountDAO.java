package com.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.entity.Account;
import com.entity.Customer;
import com.service.BankImp;

public class AccountDAO {
    private Connection connection;

    // Constructor for initializing the database connection
    public AccountDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean createAccount(Account account) {
        try {
            String insertQuery = "INSERT INTO account (customer_id, branch_id, opening_date, current_balance, interest_rate) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, account.getCustomer().getId());
            preparedStatement.setString(2, account.getBranch().getId());
            preparedStatement.setDate(3, account.getOpeningDate());
            preparedStatement.setBigDecimal(4, account.getCurrentBalance());
            preparedStatement.setBigDecimal(5, account.getInterestRate());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAccount(Account account) {
        try {
            String updateQuery = "UPDATE account SET customer_id=?, branch_id=?, opening_date=?, current_balance=?, interest_rate=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setInt(1, account.getCustomer().getId());
            preparedStatement.setString(2, account.getBranch().getId());
            preparedStatement.setDate(3, account.getOpeningDate());
            preparedStatement.setBigDecimal(4, account.getCurrentBalance());
            preparedStatement.setBigDecimal(5, account.getInterestRate());
            preparedStatement.setInt(6, account.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAccount(int accountId) {
        try {
            String deleteQuery = "DELETE FROM account WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, accountId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Account> getAccountsByCustomer(Customer customer) {
        List<Account> accounts = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM account WHERE customer_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, customer.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getInt("id"));
                // Set other account properties here
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }
	BankImp bankImp = new BankImp();

	public Account getAccountById(int accountId) throws SQLException {
		return (Account) bankImp.findAccountsOfCustomer();
	}
}
