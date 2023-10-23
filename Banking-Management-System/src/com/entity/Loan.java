package com.entity;

import java.math.BigDecimal;
import java.sql.Date;

public class Loan {
	private int id;
	private Customer customer;
	private Branch branch;
	private Date startingDate;
	private Date dueDate;
	private BigDecimal amount;
	
	public Loan() {
	}

	public Loan(int id, Customer customer, Branch branch, Date startingDate, Date dueDate, BigDecimal amount) {
		super();
		this.id = id;
		this.customer = customer;
		this.branch = branch;
		this.startingDate = startingDate;
		this.dueDate = dueDate;
		this.amount = amount;
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

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Date getStartingDate() {
		return startingDate;
	}

	public void setStartingDate(Date startingDate) {
		this.startingDate = startingDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Loan [id=" + id + ", customer=" + customer + ", branch=" + branch + ", startingDate=" + startingDate
				+ ", dueDate=" + dueDate + ", amount=" + amount + "]";
	}

	
	
}
