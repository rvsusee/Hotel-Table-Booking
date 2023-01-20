package com.htb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.htb.domain.Customer;
import com.htb.domain.Response;
import com.htb.util.ConnectionPooling;

@Repository
public class CustomerDao {

	@Autowired
	ConnectionPooling cp;

//	public Response addNewCustomer(long mobile_number, String pin_number) throws SQLException {
//		Customer customer = getCustomer(mobile_number);
//		if (customer == null) {
//			String query = "INSERT INTO HOTEL_CUSTOMER(mobile_number,pin_number) VALUES (?,?)";
//			PreparedStatement ps = cp.getConnection().prepareStatement(query);
//			ps.setLong(1, mobile_number);
//			ps.setString(2, pin_number);
//			ps.execute();
//			System.out.println("New Customer Added - Success");
//			return null;
//		} else {
//			System.out.println("Existing Customer");
//		}
//		return null;
//	}

//	
//	
//	

	public Response customerLogin(Customer customer) {
		String query = "SELECT * FROM HOTEL_CUSTOMER WHERE mobile_number = ? ;";
		Response response = new Response();
		JSONObject json = new JSONObject();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = cp.getConnection().prepareStatement(query);
			ps.setLong(1, customer.getMobileNumber());
			rs = ps.executeQuery();
			if (rs.next() == false) {
				response.setHttpStatus("OK");
				response.setMessage("Customer not found");
				return response;
			} else {
				do {
					if (rs.getString("pin_number").equals(customer.getPin())) {

						response.setHttpStatus("OK");
						response.setMessage("Login Success");

						customer.setId(rs.getInt("id"));
						customer.setName(rs.getString("name"));
						customer.setEmailId("email_id");
						customer.setPin(rs.getString("pin_number"));


//						json.put("id", customer.getId());
//						json.put("name", customer.getName());
//						json.put("mobileNumber", customer.getMobileNumber());
//						json.put("pin", customer.getPin());
//						json.put("emailId", customer.getEmailId());
						response.setObject(new JSONObject(customer));

						return response;
					}
				} while (rs.next());

				response.setHttpStatus("OK");
				response.setMessage("Pin Number Wrong");
				return response;

			}
		} catch (SQLException e) {
			response.setHttpStatus("OK");
			response.setMessage("SQLException : " + e.getLocalizedMessage());
			return response;
		}
	}

	
	
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
