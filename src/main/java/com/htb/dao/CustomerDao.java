package com.htb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.htb.domain.Customer;
import com.htb.domain.Response;
import com.htb.util.ConnectionPooling;

@Repository
public class CustomerDao {

	@Autowired
	ConnectionPooling cp;

	public Response addNewCustomer(long mobile_number, int pin_number) {
		try {
			Customer customer = getCustomer(mobile_number);
			if (customer == null) {
				String query = "INSERT INTO HOTEL_CUSTOMER(mobile_number,pin_number) VALUES (?,?)";
				PreparedStatement ps = cp.getConnection().prepareStatement(query);
				ps.setLong(1, mobile_number);
				ps.setInt(2, pin_number);
				ps.execute();
				System.out.println("New Customer Added - Success");
				return new Response("Customer Details Added Successfully", HttpStatus.OK, customer);
			} else {
				System.out.println("Existing Customer");
				return new Response("Existing Customer", HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (SQLException e) {
			System.out.println("SQL Exception - " + e.getLocalizedMessage());
			return new Response("Exception Occured" + e, HttpStatus.BAD_REQUEST);
		}
	}

	public Response customerLogin(long mobile_number, int pin_number) {
		Customer customer = null;
		try {
			customer = getCustomer(mobile_number);
		} catch (SQLException e) {
			return new Response("SQL Exception Occured", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (customer == null) {
			return new Response("User Not Found", HttpStatus.NOT_FOUND, null);
		} else if (customer.getPin() == pin_number) {
			return new Response("Login Success", HttpStatus.ACCEPTED, customer);
		} else {
			System.out.println("Wrong Pin Number");
			return new Response("User Entered Wrong Pin Number", HttpStatus.BAD_REQUEST, null);
		}
	}

	public Customer getCustomer(long mobile_number) throws SQLException {
		String query = "SELECT * FROM HOTEL_CUSTOMER WHERE mobile_number = ? ;";

		try (PreparedStatement ps = cp.getConnection().prepareStatement(query)) {
			ps.setLong(1, mobile_number);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println("Customer Details Found");
				return new Customer(rs.getInt("id"), rs.getString("name"), rs.getLong("mobile_number"),
						rs.getInt("pin_number"), rs.getString("email_id"));
			} else {
				System.out.println("Customer Details Not Found");
				return null;
			}
		}
	}

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
