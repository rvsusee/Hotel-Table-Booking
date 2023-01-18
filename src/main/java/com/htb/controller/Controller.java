package com.htb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.htb.dao.BookingDao;
import com.htb.dao.CustomerDao;
import com.htb.domain.Customer;

@RestController
public class Controller {

	@Autowired
	CustomerDao customerDao;
	@Autowired 
	BookingDao bookingDao;
	
//	add New Customer mobile_number & pin_number
	@PostMapping(value = "addNewCustomer", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public boolean addNewCustomer(@RequestBody Customer customer) throws Exception {
		System.out.println("addNewCustomer API Started");
		if (customerInputValidation(customer.getMobileNumber(), customer.getPin())) {
			return customerDao.addNewCustomer(customer.getMobileNumber(), customer.getPin());
		} else {
			return false;
		}
	}

//	check Exist Customer (mobile_number & pin_number)
	@GetMapping(value = "customerLogin", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
//	@ResponseBody
	public Customer customerLogin(@RequestBody Customer customer) throws Exception {
		System.out.println("customerLogin API Started");
		if (customerInputValidation(customer.getMobileNumber(), customer.getPin())) {
			customer = customerDao.customerLogin(customer.getMobileNumber(), customer.getPin());
			return customer;
		} else {
			return null;
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private boolean customerInputValidation(long mobile_number, int pin_number) {
		return mobileNumberValidation(mobile_number) && pinNumberValidation(pin_number) ? true : false;
	}

	private boolean mobileNumberValidation(long mobile_number) {
		if (mobile_number > 999999999 && mobile_number < new Long("10000000000")) {
			System.out.println("Validation - Mobile Number - Success");
			return true;
		} else {
			System.out.println("Validation - Mobile Number - Failed");
			return false;
		}
	}

	private boolean pinNumberValidation(int pin_number) {
		if (pin_number > 999 && pin_number < 10000) {
			System.out.println("Validation - Pin Number - Success");
			return true;
		} else {
			System.out.println("Validation - Pin Number - Failed");
			return false;
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
}
