package com.htb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.htb.dao.CustomerDao;

@RestController
public class MainController {

	@Autowired
	CustomerDao customerDao;
	MainController controller;

//	add New Customer mobile_number & pin_number
	@GetMapping(value = "addNewCustomer")
	public boolean addNewCustomer(@RequestParam long mobile_number, int pin_number) throws Exception {
		System.out.println("addNewCustomer API Started");
		if (customerInputValidation(mobile_number, pin_number)) {
			return customerDao.addNewCustomer(mobile_number, pin_number);
		} else {
			return false;
		}
	}

//	check Exist Customer (mobile_number & pin_number)
	@GetMapping(value = "customerLogin")
	public boolean isExistCustomer(@RequestParam long mobile_number, int pin_number) throws Exception {
		System.out.println("customerLogin API Started");
		if (customerInputValidation(mobile_number, pin_number)) {
			return customerDao.customerLogin(mobile_number, pin_number);
		} else {
			return false;
		}
	}

	
	
	
	private boolean customerInputValidation(long mobile_number, int pin_number) {
		return mobileNumberValidation(mobile_number) && pinNumberValidation(pin_number) ? true : false;
	}

	private boolean mobileNumberValidation(long mobile_number) {
		if (mobile_number > 999999999 && mobile_number < new Long("10000000000")) {
			System.out.println("Mobile Number Validation - Success");
			return true;
		} else {
			System.out.println("Mobile Number Validation - Failed");
			return false;
		}
	}

	private boolean pinNumberValidation(int pin_number) {
		if (pin_number > 999 && pin_number < 10000) {
			System.out.println("Pin Number Validation - Success");
			return true;
		} else {
			System.out.println("Pin Number Validation - Failed");
			return false;
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
// get booking details by id
//	@GetMapping(value = "getBooking")
//	public String getBookingById(@RequestParam Map<String, String> inputs) throws Exception {
//		return bookingService.getBookingById(inputs);
//	}
//
////	get last booking details
//	@GetMapping(value = "getLastBooking")
//	public String getLastBooking(@RequestParam(value = "mobile_number") String mobile_number) throws Exception {
//		return bookingService.getLastBooking(mobile_number);
//	}
//
////	delete booking by Id
//	@GetMapping(value = "cancelBooking")
//	public String deleteBookingById(@RequestParam Map<String, String> inputs) {
//		return bookingService.deleteBookingById(inputs);
//	}
//
//// add new Booking 
//	@PostMapping(value = "addBooking")
//	public String addBooking(@RequestParam Map<String, String> inputs) throws Exception {
//		System.out.println("AddBooking Started");
//		return bookingService.addBooking(inputs);
//	}
//
////	edit booking Incomplete
//	public String editBooking() throws Exception {
//		return "";
//	}
}
