package com.entity;

import java.util.Set;

public class Customer extends Person {
	private Set<Account> accounts;
	private Set<Loan> loans;
	
	public Customer() {
	}

	public Customer(Set<Account> accounts, Set<Loan> loans) {
		super();
		this.accounts = accounts;
		this.loans = loans;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	public Set<Loan> getLoans() {
		return loans;
	}

	public void setLoans(Set<Loan> loans) {
		this.loans = loans;
	}

	@Override
	public String toString() {
		return "Customer [accounts=" + accounts + ", loans=" + loans + "]";
	}
	
	
	
}
