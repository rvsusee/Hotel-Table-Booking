package com.htb.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.htb.dao.CustomerDao;
import com.htb.domain.Customer;
import com.htb.domain.Response;

@RestController
public class Controller {

	@Autowired
	CustomerDao customerDao;

	@GetMapping(value = "customerLogin", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response customerLogin(@RequestBody Customer customerInput) {
		Response response = new Response();

		try {
			if (Long.toString(customerInput.getMobileNumber()).length() != 10) {
				response.setHttpStatus(HttpStatus.NOT_ACCEPTABLE.toString());
				response.setMessage("Please Enter 10 digit Mobile number");
				return response;
			} else {
				List<Customer> customerList = customerDao.customerLogin(customerInput);
				if (customerList.size() == 0) {
					response.setHttpStatus(HttpStatus.NO_CONTENT.toString());
					response.setMessage("Customer Not Found");
					return response;
				} else {
					for (int i = 0; i < customerList.size(); i++) {
						if (customerList.get(i).getPin().equals(customerInput.getPin())) {
							response.setHttpStatus(HttpStatus.ACCEPTED.toString());
							response.setResponseBody(new JSONObject(customerList.get(i)));
							response.setMessage("Login Success");
							return response;
						}
					}
					response.setHttpStatus(HttpStatus.NOT_ACCEPTABLE.toString());
					response.setMessage("Pin Number Wrong");
					return response;
				}
			}
		} catch (Exception e) {
			response.setHttpStatus(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
			System.out.println("Exception" + e.getLocalizedMessage());
			response.setMessage("Something Wrong");
			return response;

		}
	}

//	add New Customer mobile_number & pin_number
	@PostMapping(value = "addNewCustomer", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response addNewCustomer(@RequestBody Customer customerInput) {
		System.out.println("addNewCustomer API");
		Response response = new Response();
		try {
			if (Long.toString(customerInput.getMobileNumber()).length() != 10) {
				response.setHttpStatus(HttpStatus.NOT_ACCEPTABLE.toString());
				response.setMessage("Please Enter 10 digit Mobile number");
				return response;
			} else {
//				check if the customer is exist or not
				List<Customer> customerList = customerDao.customerLogin(customerInput);
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
			}
		} catch (Exception e) {
			response.setHttpStatus(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
			System.out.println("Exception" + e.getLocalizedMessage());
			response.setMessage("Something Wrong");
			return response;

		}
	}

//
////		get last booking details
//		@GetMapping(value = "getLastBooking")
//		public String getLastBooking(@RequestParam(value = "mobile_number") String mobile_number) throws Exception {
//			return bookingDao.getLastBooking(mobile_number);
//		}
//
////		delete booking by Id
//		@GetMapping(value = "cancelBooking")
//		public String deleteBookingById(@RequestParam Map<String, String> inputs) {
//			return bookingDao.deleteBookingById(inputs);
//		}
//
//	// add new Booking 
//		@PostMapping(value = "addBooking")
//		public String addBooking(@RequestParam Map<String, String> inputs) throws Exception {
//			System.out.println("AddBooking Started");
//			return bookingDao.addBooking(inputs);
//		}
//
////		edit booking Incomplete
//		public String editBooking() throws Exception {
//			return "";
//		}
//	
//	

}

// get booking details by id
//	@GetMapping(value = "getBooking")
//	public String getBookingById(@RequestParam Map<String, String> inputs) throws Exception {
//		return bookingDao.getBookingById(inputs);
//	}
//
////	get last booking details
//	@GetMapping(value = "getLastBooking")
//	public String getLastBooking(@RequestParam(value = "mobile_number") String mobile_number) throws Exception {
//		return bookingDao.getLastBooking(mobile_number);
//	}
//
////	delete booking by Id
//	@GetMapping(value = "cancelBooking")
//	public String deleteBookingById(@RequestParam Map<String, String> inputs) {
//		return bookingDao.deleteBookingById(inputs);
//	}
//
//// add new Booking 
//	@PostMapping(value = "addBooking")
//	public String addBooking(@RequestParam Map<String, String> inputs) throws Exception {
//		System.out.println("AddBooking Started");
//		return bookingDao.addBooking(inputs);
//	}
//
////	edit booking Incomplete
//	public String editBooking() throws Exception {
//		return "";
//	}
