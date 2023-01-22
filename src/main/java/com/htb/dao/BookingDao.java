package com.htb.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
	ConnectionPooling connectionPooling;

//	add booking details In progress
	public boolean addBooking(BookingDetails bookingDetails) {
		try {
			String query = "exec HOTEL_ADD_BOOKING @customer_id = ? , @person_count= ?, @date_time = ?, @table_id= ?;";
			PreparedStatement preparedStatement = connectionPooling.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, bookingDetails.getCustomer().getId());
			preparedStatement.setInt(2, bookingDetails.getPersonCount());
			preparedStatement.setTimestamp(3, new Timestamp(bookingDetails.getDateTime().getTime()));
			preparedStatement.setInt(4, bookingDetails.getAllocatedTable().getTableId());

			if (preparedStatement.executeUpdate() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return false;
		}
	}

	public TableDetails checkAvailabilityTable(java.util.Date bookingDateTime) {

		
		
		return null;
	}

//	public java.sql.Timestamp convertSqlDate(String strDate) {
//		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//		Date date = null;
//		try {
//			date = (Date) sdf.parse(strDate);
//		} catch (ParseException e) {
//			System.out.println(e.getLocalizedMessage());
//		}
//		long millis = date.getTime();
//		java.sql.Timestamp ts = new Timestamp(millis);
//		return ts;
//	}

//	Cancel booking by booking Id 
	public boolean cancelBookingById(BookingDetails bookingDetails) {
		try {
			String query = "DELETE FROM HOTEL_BOOKING_DETAILS where id = ? and customer_id = ? ;";
			PreparedStatement preparedStatement = connectionPooling.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, bookingDetails.getId());
			preparedStatement.setInt(1, bookingDetails.getCustomer().getId());
			if (preparedStatement.executeUpdate() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("Exception : " + e.getLocalizedMessage());
			return false;
		}

	}

//	get last booking Details using customer id
	public List<BookingDetails> getLastBookingID(Customer customer, int lastNoOfTransaction) {
		try {
//			use join query and stored procedure
			String query = "SELECT TOP ? * FROM HOTEL_BOOKING_DETAILS where customer_id = ? ORDER BY id DESC;";
			PreparedStatement preparedStatement = connectionPooling.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, lastNoOfTransaction);
			preparedStatement.setInt(2, customer.getId());
			ResultSet resultSet = preparedStatement.executeQuery();
			List<BookingDetails> bookings = null;
			while (resultSet.next()) {
				BookingDetails bookingDetails = new BookingDetails();
				bookingDetails.setId(resultSet.getInt("id"));
				bookingDetails.setCustomer(customer);
				bookingDetails.setPersonCount(resultSet.getInt("person_count"));
				bookingDetails.setDateTime(resultSet.getDate("date_time"));
				bookingDetails.setBookingOn(resultSet.getDate("booking_on"));
				bookingDetails.setAllocatedTable(
						new TableDetails(resultSet.getInt("table_id"), resultSet.getInt("table_size")));
				bookings.add(bookingDetails);
			}
			return bookings;
		} catch (Exception e) {
			System.out.println("Exception : " + e.getLocalizedMessage());
			return null;
		}

	}

//	get booking details by Booking id In progress
	public BookingDetails getBookingById(BookingDetails bookingDetails) {
		try {
//			use join query and stored procedure
			String query = "SELECT  * FROM HOTEL_BOOKING_DETAILS where id = ? and customer_id = ?;";
			PreparedStatement preparedStatement = connectionPooling.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, bookingDetails.getId());
			preparedStatement.setInt(2, bookingDetails.getCustomer().getId());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				bookingDetails.setId(resultSet.getInt("id"));
				bookingDetails.setCustomer(bookingDetails.getCustomer());
				bookingDetails.setPersonCount(resultSet.getInt("person_count"));
				bookingDetails.setDateTime(resultSet.getDate("date_time"));
				bookingDetails.setBookingOn(resultSet.getDate("booking_on"));
				bookingDetails.setAllocatedTable(
						new TableDetails(resultSet.getInt("table_id"), resultSet.getInt("table_size")));
				return bookingDetails;
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println("Exception : " + e.getLocalizedMessage());
			return null;
		}
	}

//	
//	
//	
//	
//	
//	
//	
//	
//	get Branch Details using Brach Id
	public BranchDetails getBranchDetailsById(int branch_id) {
		try {
			String query = "SELECT * FROM HOTEL_BRANCH where id = ? ;";
			PreparedStatement preparedStatement = connectionPooling.getConnection().prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return null;
			} else {
				List<TableDetails> tablesDetails = getTableDetails(resultSet.getInt("id"));
				if (tablesDetails == null || tablesDetails.size() == 0) {
					return null;
				} else {
					return new BranchDetails(resultSet.getInt("id"), resultSet.getString("name"),
							resultSet.getString("address"), tablesDetails);
				}
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e.toString());
			return null;
		}
	}

//	get table Details using Brach Id
	public ArrayList<TableDetails> getTableDetails(int branch_id) {
		try {
			String query = "SELECT * FROM HOTEL_BRANCH where id = ? ;";
			PreparedStatement preparedStatement = connectionPooling.getConnection().prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<TableDetails> tableDetails = null;
			while (resultSet.next()) {
				tableDetails.add(new TableDetails(resultSet.getInt("id"), resultSet.getInt("size")));
			}
			return tableDetails;
		} catch (Exception e) {
			System.out.println("Exception: " + e.toString());
			return null;
		}
	}

}