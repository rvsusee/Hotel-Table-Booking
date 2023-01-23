package com.htb.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.htb.dao.BookingDao;
import com.htb.dao.CustomerDao;
import com.htb.domain.BookingDetails;
import com.htb.domain.Customer;
import com.htb.domain.Response;

@RestController
@RequestMapping("/customer/")
public class CustomerController {

	@Autowired
	CustomerDao customerDao;

	@Autowired
	BookingDao bookingDao;

	@GetMapping(value = "login", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response customerLogin(@RequestBody Customer customerInput) {
		System.out.println("Customer Login API");
		Response response = new Response();
		try {
			if (Long.toString(customerInput.getMobileNumber()).length() != 10) {
				System.out.println("Mobile Number validation Failed");
				response.setHttpStatus(HttpStatus.NOT_ACCEPTABLE.toString());
				response.setMessage("Please Enter 10 digit Mobile number");
				return response;
			} else {
				System.out.println("Mobile Number validation Success");
				List<Customer> customerList = customerDao.customerLogin(customerInput);
				if (customerList != null) {
					if (customerList.size() == 0) {
						System.out.println("Customer Details Not Found");
						response.setHttpStatus(HttpStatus.NO_CONTENT.toString());
						response.setMessage("Customer Not Found");
						return response;
					} else {
						System.out.println("Customer Details found in DB");
						for (int i = 0; i < customerList.size(); i++) {
							if (customerList.get(i).getPin().equals(customerInput.getPin())) {
								response.setHttpStatus(HttpStatus.ACCEPTED.toString());
								response.setResponseBody(new JSONObject(customerList.get(i)));
								response.setMessage("Login Success");
								return response;
							}
						}
						response.setHttpStatus(HttpStatus.NOT_ACCEPTABLE.toString());
						System.out.println("Pin Number Wrong");
						response.setMessage("Pin Number Wrong");
						return response;
					}
				} else {
					response.setHttpStatus(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
					System.out.println("Database Connection Error");
					response.setMessage("Something Wrong");
					return response;
				}
			}
		} catch (NumberFormatException e) {
			response.setHttpStatus(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
			System.out.println("NumberFormatException" + e.getLocalizedMessage());
			response.setMessage("Please Enter 10 digit Mobile Number");
			return response;
		} catch (Exception e) {
			response.setHttpStatus(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
			System.out.println("Exception" + e.getLocalizedMessage());
			response.setMessage("Something Wrong");
			return response;
		}
	}

	@PostMapping(value = "register", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response newCustomer(@RequestBody Customer customerInput) {
		System.out.println("addNewCustomer API");
		Response response = new Response();
		try {
			if (Long.toString(customerInput.getMobileNumber()).length() != 10) {
				response.setHttpStatus(HttpStatus.NOT_ACCEPTABLE.toString());
				response.setMessage("Please Enter 10 digit Mobile number");
				return response;
			} else {
				List<Customer> customerList = customerDao.customerLogin(customerInput);
				if (customerList != null) {
					if (customerList.size() == 0) {
						boolean customer = customerDao.addNewCustomer(customerInput);
						if (customer == false) {
							response.setHttpStatus(HttpStatus.BAD_REQUEST.toString());
							response.setMessage("Unable to Create Account");
							return response;
						} else {
							response.setHttpStatus(HttpStatus.ACCEPTED.toString());
							response.setMessage("Successfully Created");
							return response;
						}
					} else {
						response.setHttpStatus(HttpStatus.NOT_ACCEPTABLE.toString());
						response.setMessage("Existing Customer");
						return response;
					}
				} else {
					response.setHttpStatus(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
					response.setMessage("Something Wrong");
					return response;
				}
			}
		} catch (NumberFormatException e) {
			response.setHttpStatus(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
			System.out.println("NumberFormatException" + e.getLocalizedMessage());
			response.setMessage("Please Enter 10 digit Mobile Number");
			return response;
		} catch (Exception e) {
			response.setHttpStatus(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
			System.out.println("Exception" + e.getLocalizedMessage());
			response.setMessage("Something Wrong");
			return response;
		}
	}
}