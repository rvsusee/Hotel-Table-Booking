package com.htb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
//	public boolean addBooking(BookingDetails bookingDetails) {
//		try {
//			String date_time = inputs.get("date_time");
//			Timestamp date_timeSql = new DateTimeValidation().convertSqlDate(date_time);
//			String query = "exec HOTEL_ADD_BOOKING @customer_id = " + customer.getId() + ", @person_count= "
//					+ person_count + ", @date_time = '" + date_timeSql + "', @table_id= 100;";
//
//			boolean bk = bkDao.addBooking(query, customer);
//			if (bk == false) {
//				return "Booking Details Not Found";
//			} else {
//				return "Booking Added Successfully";
//			}
//		} catch (Exception e) {
//		}
//	}

//	public BookingDetails getBookingById(String query, Customer customer) {
//		ResultSet resultSet;
//		try {
//			resultSet = connectionPooling.getConnection().createStatement().executeQuery(query);
//			if (resultSet.next())
//				return new BookingDetails(resultSet.getInt("id"), customer, resultSet.getInt("person_count"),
//						resultSet.getDate("date_time"), getTableDetailsById(resultSet.getInt("table_id")),
//						resultSet.getDate("booking_on"));
//			else
//				System.out.println("BookingDetails Not Found");
//		} catch (SQLException e) {
//			System.out.println("Exception Occured in fetchBookingById : " + e.toString());
//		}
//		return null;
//	}

//	public void updateBookingById(BookingDetails bk, int bkId) {
//// INCOMPLETE
//	}

//	public boolean deleteBookingById(String query) {
//		try {
//			int delete = connectionPooling.getConnection().createStatement().executeUpdate(query);
//			if (delete != 0)
//				return true;
//			else
//				return false;
//		} catch (SQLException e) {
//			System.out.println("Exception Occured in AddNewCustomer(CustomerDao) - " + e.toString());
//			return false;
//		}
//	}

//	get bookingDetails
	public List<BookingDetails> getLastBookingID(BookingDetails bookingDetails) {
		try {

			if()
			
			String query = "SELECT TOP 1 * FROM HOTEL_BOOKING_DETAILS where customer_id = ? ORDER BY id DESC;";
			PreparedStatement preparedStatement = connectionPooling.getConnection().prepareStatement(query)
					preparedStatement.setInt(1, bookingDetails.getCustomer().getId());
			ResultSet resultSet = preparedStatement.executeQuery();
			List<BookingDetails> bookings = null;
			while (resultSet.next()) {

				bookings.add(new BookingDetails(resultSet.getInt("id"), bookingDetails.getCustomer(), resultSet.getInt("person_count"), null, null, null))
			}
		} catch (Exception e) {

		}

		return null;
	}

	// get table details using table Id
//	public TableDetails getTableDetailsById(int id) {
//		String query = "SELECT * FROM HOTEL_TABLE where id = " + id + ";";
//		ResultSet rs;
//		try {
//			rs = cp.getConnection().createStatement().executeQuery(query);
//			if (rs.next())
//				return new TableDetails(rs.getInt("id"), rs.getInt("size"));
//			else
//				System.out.println("No Data Found");
//		} catch (SQLException e) {
//			System.out.println("SQL Exception Occured in GetCustomerDetailsByMobileNumber Method " + e.toString());
//		}
//		return null;
//	}

//	get branch Details using branch Id
//	public BranchDetails getBranchDetailsById(int id) {
//		String query = "SELECT * FROM HOTEL_BRANCH where id = " + id + ";";
//		ResultSet rs;
//		try {
//			rs = cp.getConnection().createStatement().executeQuery(query);
//			if (rs.next())
//				return new BranchDetails(rs.getInt("id"), rs.getString("name"), rs.getString("address"),
//						new ArrayList<>());
//			else
//				System.out.println("No Data Found");
//		} catch (SQLException e) {
//			System.out.println("SQL Exception Occured in GetCustomerDetailsByMobileNumber Method " + e.toString());
//		}
//		return null;
//	}

//	get table Details using Brach Id
//	public ArrayList<TableDetails> tableDetails(int branch_id) {
//		String query = "SELECT * FROM HOTEL_BRANCH where id = " + branch_id + ";";
//		ResultSet rs;
//		try {
//			rs = cp.getConnection().createStatement().executeQuery(query);
//			ArrayList<TableDetails> tableDetails = null;
//			while (rs.next()) {
//				tableDetails.add(new TableDetails(rs.getInt("id"), rs.getInt("size")));
//			}
//			return tableDetails;
//		} catch (SQLException e) {
//			System.out.println("SQL Exception Occured in GetCustomerDetailsByMobileNumber Method " + e.toString());
//		}
//		return null;
//	}

}
