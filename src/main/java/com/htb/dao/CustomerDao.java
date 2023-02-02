package com.htb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.htb.domain.Customer;
import com.htb.util.ConnectionPooling;

@Repository
public class CustomerDao {

	@Autowired
	ConnectionPooling connectionPooling;

	Logger logger = LogManager.getLogger("HotelTableBooking");

	public Customer customerLogin(long mobileNumber) {
		logger.info("customerLogin");
		String query = "exec HOTEL_GET_CUSTOMER_BY_MOBILENUMBER @mobile_number = ?;";
		try {
			PreparedStatement preparedStatement = connectionPooling.getConnection().prepareStatement(query);
			preparedStatement.setLong(1, mobileNumber);
			ResultSet rs = preparedStatement.executeQuery();
			Customer customerDB = null;
			while (rs.next()) {
				customerDB = new Customer(rs.getInt("id"), rs.getString("name"), rs.getLong("mobile_number"),
						rs.getString("pin_number"), rs.getString("email_id"));
			}
			return customerDB;
		} catch (SQLException e) {
			logger.fatal("SQLException : " + e.getLocalizedMessage());
			return null;
		} catch (Exception e) {
			logger.fatal("Exception : " + e.getLocalizedMessage());
			return null;
		}
	}

	public boolean addNewCustomer(Customer customer) {
		logger.info("addNewCustomer");
		try {
			String query = "exec HOTEL_ADD_NEW_CUSTOMER @mobile_number = ?, @pin_number = ?;";
			PreparedStatement preparedStatement = connectionPooling.getConnection().prepareStatement(query);
			preparedStatement.setLong(1, customer.getMobileNumber());
			preparedStatement.setString(2, customer.getPin());
			if (preparedStatement.executeUpdate() > 0) {
				logger.info("data inserted successfully");
				return true;
			} else {
				logger.error("data insertion failed");
				return false;
			}
		} catch (SQLException e) {
			logger.fatal("SQLException" + e.getLocalizedMessage());
			return false;
		} catch (Exception e) {
			logger.fatal("Exception" + e.getLocalizedMessage());
			return false;
		}
	}

}
