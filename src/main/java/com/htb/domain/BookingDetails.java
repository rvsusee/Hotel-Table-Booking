
package com.htb.domain;

import java.sql.Date;

public class BookingDetails {

	private int id;
	private Customer customer;
	private int personCount;
	private Date dateTime;
	private TableDetails allocatedTable;
	private Date bookingOn;

	public BookingDetails(int id, Customer customer, int personCount, Date dateTime, TableDetails allocatedTable,
			Date bookingOn) {
		this.id = id;
		this.customer = customer;
		this.personCount = personCount;
		this.dateTime = dateTime;
		this.allocatedTable = allocatedTable;
		this.bookingOn = bookingOn;
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

	public int getPersonCount() {
		return personCount;
	}

	public void setPersonCount(int personCount) {
		this.personCount = personCount;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public TableDetails getAllocatedTable() {
		return allocatedTable;
	}

	public void setAllocatedTable(TableDetails allocatedTable) {
		this.allocatedTable = allocatedTable;
	}

	public Date getBookingOn() {
		return bookingOn;
	}

	public void setBookingOn(Date bookingOn) {
		this.bookingOn = bookingOn;
	}

	@Override
	public String toString() {
		return "BookingDetails [id=" + id + ", customer=" + customer + ", personCount=" + personCount + ", dateTime="
				+ dateTime + ", allocatedTable=" + allocatedTable + ", bookingOn=" + bookingOn + "]";
	}

}
