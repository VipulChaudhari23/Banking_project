package com.entity;

import java.sql.Date;

public abstract class Person {
	private int id;
	
	private String login;
	private String plainPassword;
	
	private String name;
	private String phone;
	private String email;
	
	private Date registrationDate;

	// Default Constructor
	public Person() {
	}

	// PArameteraized Constructor
	public Person(int id, String login, String plainPassword, String name, String phone, String email, Date registrationDate) {
		super();
		this.id = id;
		this.login = login;
		this.plainPassword = plainPassword;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.registrationDate = registrationDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPasshash() {
		return plainPassword;
	}

	public void setPasshash(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", login=" + login + ", passhash=" + plainPassword + ", name=" + name + ", phone="
				+ phone + ", email=" + email + ", registrationDate=" + registrationDate + "]";
	}
	
	
}
