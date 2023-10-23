package com.entity;

public class Branch {
	public static final Branch PUNE = null;
	public static final Branch MUMBAI = null;
	private String id;
	private String branch;
	private String phone;
	
	public Branch() {
	}

	public Branch(String id, String branch, String phone) {
		super();
		this.id = id;
		this.branch = branch;
		this.phone = phone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getbranch() {
		return branch;
	}

	public void setbranch(String branch) {
		this.branch = branch;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Branch [id=" + id + ", branch=" + branch + ", phone=" + phone + "]";
	}
}
