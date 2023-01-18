package com.htb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.htb.domain.Customer;
import com.htb.util.ConnectionPooling;

@Repository
public class CustomerDao {

	@Autowired
	ConnectionPooling cp;

	public boolean addNewCustomer(long mobile_number, int pin_number) {
		if (getCustomer(mobile_number) == null) {
			String query = "INSERT INTO HOTEL_CUSTOMER(mobile_number,pin_number) VALUES (?,?)";
			try (PreparedStatement ps = cp.getConnection().prepareStatement(query)) {
				ps.setLong(1, mobile_number);
				ps.setInt(2, pin_number);
				ps.execute();
				System.out.println("New Customer Added - Success");
				return true;
			} catch (SQLException e) {
				System.out.println("SQL Exception - " + e.getLocalizedMessage());
			}
			return false;
		} else {
			System.out.println("Existing Customer");
			return false;
		}
	}

	public Customer customerLogin(long mobile_number, int pin_number) {
		Customer customer = getCustomer(mobile_number);
		if (customer == null) {
//			return false;
		} else if (customer.getPin() == pin_number) {
			return customer;
		} else {
			System.out.println("Wrong Pin Number");
//			return false;
		}
		return null;
	}

	
	
	
	public Customer getCustomer(long mobile_number) {
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
		} catch (SQLException e) {
			System.out.println("SQL Exception - " + e.getMessage());
			return null;
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
