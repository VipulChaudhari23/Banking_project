package com.entity;

import java.math.BigDecimal;
import java.sql.Date;

public class Account {
	private int id;
	private Customer customer;
	private Branch branch;
	private Date openingDate;
	private BigDecimal currentBalance;
	private BigDecimal interestRate;
	
	public Account() {
	}

	public Account(int id, Customer customer, Branch branch, Date openingDate, BigDecimal currentBalance,
			BigDecimal interestRate) {
		super();
		this.id = id;
		this.customer = customer;
		this.branch = branch;
		this.openingDate = openingDate;
		this.currentBalance = currentBalance;
		this.interestRate = interestRate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branchId) {
		this.branch = branchId;
	}

	public Date getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", customer=" + customer + ", branch=" + branch + ", openingDate=" + openingDate
				+ ", currentBalance=" + currentBalance + ", interestRate=" + interestRate + "]";
	}
	
	

}
