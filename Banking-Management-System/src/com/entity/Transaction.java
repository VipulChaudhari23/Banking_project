package com.entity;

import java.math.BigDecimal;
import java.sql.Date;

public class Transaction {
	private int id;
	private Date date;
	private BigDecimal amount;
	private Account account;
	private Employee teller;
	
	public Transaction() {
	}

	public Transaction(int id, Date date, BigDecimal amount, Account account, Employee teller) {
		super();
		this.id = id;
		this.date = date;
		this.amount = amount;
		this.account = account;
		this.teller = teller;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Employee getTeller() {
		return teller;
	}

	public void setTeller(Employee teller) {
		this.teller = teller;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", date=" + date + ", amount=" + amount + ", account=" + account + ", teller="
				+ teller + "]";
	}

}
