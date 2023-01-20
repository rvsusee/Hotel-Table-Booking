package com.htb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.htb.domain.Customer;
import com.htb.util.ConnectionPooling;

@Repository
public class CustomerDao {

	@Autowired
	ConnectionPooling connectionPooling;

	public List<Customer> customerLogin(Customer customer) {
		String query = "SELECT * FROM HOTEL_CUSTOMER WHERE mobile_number = ? ;";
		try {
			PreparedStatement ps = connectionPooling.getConnection().prepareStatement(query);
			ps.setLong(1, customer.getMobileNumber());
			ResultSet rs = ps.executeQuery();
			List<Customer> customerList = new ArrayList<>();
			while (rs.next()) {
				customerList.add(new Customer(rs.getInt("id"), rs.getString("name"), rs.getLong("mobile_number"),
						rs.getString("pin_number"), rs.getString("email_id")));
			}
			return customerList;
		} catch (SQLException e) {
			System.out.println("SQLException" + e.getLocalizedMessage());
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
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	

//	public Response addNewCustomer(Customer customer) {
//		String query = "SELECT * FROM HOTEL_CUSTOMER WHERE mobile_number = ? ;";
//		Response response = new Response();
//		JSONObject json = new JSONObject();
//
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try {
//			ps = cp.getConnection().prepareStatement(query);
//			ps.setLong(1, customer.getMobileNumber());
//			rs = ps.executeQuery();
//			if (rs.next() == true) {
//				response.setHttpStatus(HttpStatus.BAD_REQUEST.toString());
//				response.setMessage("Existing Customer");
//				return response;
//			} else {
//
//				query = "INSERT INTO HOTEL_CUSTOMER(mobile_number,pin_number) VALUES (?,?)";
//				ps = cp.getConnection().prepareStatement(query);
//				ps.setLong(1, customer.getMobileNumber());
//				ps.setString(2, customer.getPin());
//				if (ps.executeUpdate() > 0) {
//					response.setHttpStatus(HttpStatus.OK.getReasonPhrase());
//					response.setMessage("Account Created Successfully");
//					return response;
//				} else {
//					response.setHttpStatus(HttpStatus.OK.getReasonPhrase());
//					response.setMessage("Unable to create Account");
//					return response;
//
//				}
//			}
//		} catch (SQLException e) {
//			response.setHttpStatus("OK");
//			response.setMessage("SQLException : " + e.getLocalizedMessage());
//			return response;
//		}
//
//	}
//	
//	public Customer getCustomer(long mobile_number) throws SQLException {
//		String query = "SELECT * FROM HOTEL_CUSTOMER WHERE mobile_number = ? ;";
//
//		PreparedStatement ps = cp.getConnection().prepareStatement(query);
//		ps.setLong(1, mobile_number);
//		ResultSet rs = ps.executeQuery();
//		if (rs.next()) {
//			System.out.println("Customer Details Found");
//			return new Customer(rs.getInt("id"), rs.getString("name"), rs.getLong("mobile_number"),
//					rs.getString("pin_number"), rs.getString("email_id"));
//		} else {
//			System.out.println("Customer Details Not Found");
//			return null;
//		}
//	}

//	public boolean addNewCustomer(String query) {
//		try {
//			cp.getConnection().createStatement().execute(query);
//			return true;
//		} catch (SQLException e) {
//			System.out.println("Exception Occured in AddNewCustomer(CustomerDao) - " + e.toString());
//			return false;
//		}
//	}

}
