package com.htb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.htb.domain.BookingDetails;
import com.htb.domain.BranchDetails;
import com.htb.domain.Customer;
import com.htb.domain.TableDetails;
import com.htb.util.ConnectionPooling;

@Repository
public class BookingDao {

	@Autowired
	ConnectionPooling cp;

	public boolean addBooking(String query, Customer customer) {
		try {
			cp.getConnection().createStatement().executeUpdate(query);
			return true;
		} catch (SQLException e) {
			System.out.println("SQL Exception Occured in addBooking Method " + e.toString());
		}
		return false;
	}

	public BookingDetails getBookingById(String query, Customer customer) {
		ResultSet rs;
		try {
			rs = cp.getConnection().createStatement().executeQuery(query);
			if (rs.next())
				return new BookingDetails(rs.getInt("id"), customer, rs.getInt("person_count"), rs.getDate("date_time"),
						getTableDetailsById(rs.getInt("table_id")), rs.getDate("booking_on"));
			else
				System.out.println("BookingDetails Not Found");
		} catch (SQLException e) {
			System.out.println("Exception Occured in fetchBookingById : " + e.toString());
		}
		return null;
	}

	public void updateBookingById(BookingDetails bk, int bkId) {
// INCOMPLETE
	}

	public boolean deleteBookingById(String query) {
		try {
			int delete = cp.getConnection().createStatement().executeUpdate(query);
			if (delete != 0)
				return true;
			else
				return false;
		} catch (SQLException e) {
			System.out.println("Exception Occured in AddNewCustomer(CustomerDao) - " + e.toString());
			return false;
		}
	}

//	get bookingDetails
	public BookingDetails getLastBookingID(String query, Customer customer) {
		return getBookingById(query, customer);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// get table details using table Id
	public TableDetails getTableDetailsById(int id) {
		String query = "SELECT * FROM HOTEL_TABLE where id = " + id + ";";
		ResultSet rs;
		try {
			rs = cp.getConnection().createStatement().executeQuery(query);
			if (rs.next())
				return new TableDetails(rs.getInt("id"), rs.getInt("size"));
			else
				System.out.println("No Data Found");
		} catch (SQLException e) {
			System.out.println("SQL Exception Occured in GetCustomerDetailsByMobileNumber Method " + e.toString());
		}
		return null;
	}

//	get branch Details using branch Id
	public BranchDetails getBranchDetailsById(int id) {
		String query = "SELECT * FROM HOTEL_BRANCH where id = " + id + ";";
		ResultSet rs;
		try {
			rs = cp.getConnection().createStatement().executeQuery(query);
			if (rs.next())
				return new BranchDetails(rs.getInt("id"), rs.getString("name"), rs.getString("address"),
						new ArrayList<>());
			else
				System.out.println("No Data Found");
		} catch (SQLException e) {
			System.out.println("SQL Exception Occured in GetCustomerDetailsByMobileNumber Method " + e.toString());
		}
		return null;
	}

//	get table Details using Brach Id
	public ArrayList<TableDetails> tableDetails(int branch_id) {
		String query = "SELECT * FROM HOTEL_BRANCH where id = " + branch_id + ";";
		ResultSet rs;
		try {
			rs = cp.getConnection().createStatement().executeQuery(query);
			ArrayList<TableDetails> tableDetails = null;
			while (rs.next()) {
				tableDetails.add(new TableDetails(rs.getInt("id"), rs.getInt("size")));
			}
			return tableDetails;
		} catch (SQLException e) {
			System.out.println("SQL Exception Occured in GetCustomerDetailsByMobileNumber Method " + e.toString());
		}
		return null;
	}

}
