package com.htb.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.htb.dao.BookingDao;
import com.htb.dao.CustomerDao;
import com.htb.domain.Customer;
import com.htb.domain.Response;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerDao customerDao;

	@Autowired
	BookingDao bookingDao;

	Logger logger = LogManager.getLogger("HotelTableBooking");

	@GetMapping("/t1")
	public String response() {
		return "Testing";
	}

	@GetMapping(value = "/getExistCustomer/{mobileNumberInput}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response customerLogin(@PathVariable String mobileNumberInput) {
		Response response = new Response();

		logger.info("getExistCustomer API");
		try {
			if (mobileNumberInput.length() != 10) {
				logger.warn("Mobile Number Validation - Failed");
				response.setHttpStatus(HttpStatus.NOT_ACCEPTABLE);
				response.setMessage("Mobile Number Validation - Failed");
				return response;
			} else {
				logger.info("Mobile Number Validation - Success");
				long mobileNumber = Long.parseLong(mobileNumberInput);
				Customer customerDB = customerDao.customerLogin(mobileNumber);
				if (customerDB != null) {
					logger.info("Customer Details Found in Database");
					response.setHttpStatus(HttpStatus.ACCEPTED);
					response.setResponseBody(new JSONObject(customerDB));
					response.setMessage("Login - Success");
					return response;

//					if (customerDB.getPin().equals(customerInput.getPin())) {
//						logger.info("Customer Login Success");
//						response.setHttpStatus(HttpStatus.ACCEPTED);
//						response.setResponseBody(new JSONObject(customerDB));
//						response.setMessage("Login - Success");
//						return response;
//					} else {
//						response.setHttpStatus(HttpStatus.NOT_ACCEPTABLE);
//						logger.warn("Login - Failed");
//						logger.warn("Pin Number Wrong");
//						response.setMessage("Login Failed - Pin Number Wrong");
//						return response;
//					}
				} else {
					logger.info("Customer Details Not Found in database");
					response.setHttpStatus(HttpStatus.NO_CONTENT);
					response.setMessage("Login Failed - Customer Not Found");
					return response;
				}
			}
		} catch (NumberFormatException e) {
			response.setHttpStatus(HttpStatus.SERVICE_UNAVAILABLE);
			logger.fatal("NumberFormatException" + e.getLocalizedMessage());
			response.setMessage("NumberFormatException Occured");
			return response;
		} catch (Exception e) {
			response.setHttpStatus(HttpStatus.SERVICE_UNAVAILABLE);
			logger.fatal("Exception" + e.getLocalizedMessage());
			response.setMessage("Exception Occured");
			return response;
		}
	}

	@PostMapping(value = "/register", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response newCustomer(@RequestBody Customer customerInput) {
		logger.info("register API");
		Response response = new Response();
		try {
			if (Long.toString(customerInput.getMobileNumber()).length() != 10) {
				logger.warn("Mobile Number Validation - Failed");
				response.setHttpStatus(HttpStatus.NOT_ACCEPTABLE);
				response.setMessage("Mobile Number Validation - Failed");
				return response;
			} else {
				logger.info("Mobile Number Validation - Success");
				Customer customerDB = customerDao.customerLogin(customerInput.getMobileNumber());
				if (customerDB == null) {
					logger.info("New Customer");
					boolean customer = customerDao.addNewCustomer(customerInput);
					if (customer == false) {
						logger.warn("Unable to create customer");
						response.setHttpStatus(HttpStatus.BAD_REQUEST);
						response.setMessage("Account Creation - Failed");
						return response;
					} else {
						logger.info("Account Created Success");
						response.setHttpStatus(HttpStatus.ACCEPTED);
						response.setMessage("Account Creation - Success");
						return response;
					}
				} else {
					logger.error("Existing Customer or Database Error");
					response.setHttpStatus(HttpStatus.NOT_ACCEPTABLE);
					response.setMessage("Account Creation Failed");
					return response;
				}
			}
		} catch (NumberFormatException e) {
			logger.fatal("NumberFormatException Occured: " + e.getLocalizedMessage());
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			response.setMessage("Mobile Number Format Error ");
			return response;
		} catch (Exception e) {
			response.setHttpStatus(HttpStatus.SERVICE_UNAVAILABLE);
			logger.fatal("Exception" + e.getLocalizedMessage());
			response.setMessage("Exception Occured");
			return response;
		}
	}
}