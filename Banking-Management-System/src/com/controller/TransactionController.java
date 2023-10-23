package com.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.entity.Account;
import com.entity.Transaction;
import com.service.BankImp;

public class TransactionController {
	
	BankImp transbankImp = new BankImp(); 

	public boolean deposite() throws SQLException{
		return transbankImp.addDeposit();
	};
	
	public boolean transfer() throws SQLException {
		return transbankImp.transfer();
		
	}
	
	public boolean withdraw() throws SQLException {
		return transbankImp.withdraw();
		
	}
	
	public String getTransactionDetails(Transaction transaction) {
		return transbankImp.getTransactionDetails(transaction);
	}	

}
