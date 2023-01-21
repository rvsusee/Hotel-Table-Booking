package com.htb.domain;

public class Customer {
	private int id;
	private String name;
	private long mobileNumber;
	private String pin;
	private String emailId;

	public Customer(int id, String name, long mobileNumber, String pin, String emailId) {
		this.id = id;
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.pin = pin;
		this.emailId = emailId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", mobileNumber=" + mobileNumber + ", pin=" + pin
				+ ", emailId=" + emailId + "]";
	}
	
	
}
