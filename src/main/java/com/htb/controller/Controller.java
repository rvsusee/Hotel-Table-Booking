package com.htb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
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

//	check Exist Customer (mobile_number & pin_number)
	@GetMapping(value = "customerLogin", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response customerLogin(@RequestBody Customer customer) throws Exception {
		System.out.println("customerLogin API ");
		String inputValidation = mobileNumberValidation(customer.getMobileNumber());
		if (inputValidation.equals("success")) {

			return customerDao.customerLogin(customer);
		} else {
			return new Response("OK", inputValidation);
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
//	
//	
//	
//	
//	
//	
//	
//	

//	add New Customer mobile_number & pin_number
//	@PostMapping(value = "addNewCustomer", consumes = { MediaType.APPLICATION_JSON_VALUE, }, produces = {
//			MediaType.APPLICATION_JSON_VALUE })
//	public @ResponseBody JSONObject addNewCustomer(@RequestBody Customer customer) {
//		System.out.println("addNewCustomer API");
//
//		JSONObject response = new JSONObject();
//
////		Response response = new Response();
//
//		response.setMessage(mobileNumberValidation(customer.getMobileNumber()));
//
//		if (response.getMessage().equals("success")) {
//			response = customerDao.addNewCustomer(customer.getMobileNumber(), customer.getPin());
//			return response;
//		} else {
//			response.setHttpStatus(HttpStatus.NOT_ACCEPTABLE);
//			return response;
//		}
//	}

//	
//	// get booking details by id
//		@GetMapping(value = "getBooking")
//		public String getBookingById(@RequestParam Map<String, String> inputs) throws Exception {
//			return bookingDao.getBookingById(inputs);
//		}
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

	private String mobileNumberValidation(long mobile_number) {
		if (Long.toString(mobile_number).length() == 10) {
			System.out.println("Validation - Mobile Number - Success");
			return "success";
		} else {
			System.out.println("Validation - Mobile Number - Failed");
			return "Please Enter 10 digit Mobile number";
		}
	}
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
