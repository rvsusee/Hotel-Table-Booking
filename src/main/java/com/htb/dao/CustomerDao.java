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
			PreparedStatement preparedStatement = connectionPooling.getConnection().prepareStatement(query);
			preparedStatement.setLong(1, customer.getMobileNumber());
			ResultSet rs = preparedStatement.executeQuery();
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

	public boolean addNewCustomer(Customer customer) {
		try {
			String query = "INSERT INTO HOTEL_CUSTOMER(mobile_number,pin_number) VALUES (?,?)";
			PreparedStatement preparedStatement = connectionPooling.getConnection().prepareStatement(query);
			preparedStatement.setLong(1, customer.getMobileNumber());
			preparedStatement.setString(2, customer.getPin());
			if (preparedStatement.executeUpdate() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			System.out.println("SQLException" + e.getLocalizedMessage());
			return false;
		}
	}

}
