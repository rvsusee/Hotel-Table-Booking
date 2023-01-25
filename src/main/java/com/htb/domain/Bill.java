package com.htb.domain;

public class Bill {

	private int id;

	private BookingDetails bookingDetails;

	private double amount;

	public Bill(int id, BookingDetails bookingDetails, double amount) {
		this.id = id;
		this.bookingDetails = bookingDetails;
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BookingDetails getBookingDetails() {
		return bookingDetails;
	}

	public void setBookingDetails(BookingDetails bookingDetails) {
		this.bookingDetails = bookingDetails;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
