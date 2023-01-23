package com.htb.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/customer/")
public class CustomerController {

	@Autowired
	CustomerDao customerDao;

	@Autowired
	BookingDao bookingDao;

	Logger logger = LogManager.getLogger("Customer Controller");

	@GetMapping(value = "login", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response customerLogin(@RequestBody Customer customerInput) {
		Response response = new Response();
		logger.info("login API");
		try {
			if (Long.toString(customerInput.getMobileNumber()).length() != 10) {
				logger.warn("Mobile Number Validation - Failed");
				response.setHttpStatus(HttpStatus.NOT_ACCEPTABLE);
				response.setMessage("Mobile Number Validation Failed");
				return response;
			} else {
				logger.info("Mobile Number Validation - Success");
				List<Customer> customerList = customerDao.customerLogin(customerInput);
				if (customerList != null) {
					if (customerList.size() == 0) {
						logger.info("Customer Details Not Found in database");
						response.setHttpStatus(HttpStatus.NO_CONTENT);
						response.setMessage("Customer Not Found");
						return response;
					} else {
						logger.info("Customer Details Found in Database");
						for (int customer = 0; customer < customerList.size(); customer++) {
							if (customerList.get(customer).getPin().equals(customerInput.getPin())) {
								logger.info("Customer Login Success");
								response.setHttpStatus(HttpStatus.ACCEPTED);
								response.setResponseBody(new JSONObject(customerList.get(customer)));
								response.setMessage("Login Success");
								return response;
							}
						}
						response.setHttpStatus(HttpStatus.NOT_ACCEPTABLE);
						logger.info("Pin Number Wrong");
						response.setMessage("Pin Number Wrong");
						return response;
					}
				} else {
					response.setHttpStatus(HttpStatus.SERVICE_UNAVAILABLE);
					logger.error("Database Connection Failed");
					response.setMessage("Database Connection Failed");
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

	@PostMapping(value = "register", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response newCustomer(@RequestBody Customer customerInput) {
		logger.info("register API");
		Response response = new Response();
		try {
			if (Long.toString(customerInput.getMobileNumber()).length() != 10) {
				logger.warn("Mobile Number Validation Failed");
				response.setHttpStatus(HttpStatus.NOT_ACCEPTABLE);
				response.setMessage("Mobile Number Validation Failed");
				return response;
			} else {
				logger.info("Mobile Number Validation Success");
				List<Customer> customerList = customerDao.customerLogin(customerInput);
				if (customerList != null) {
					if (customerList.size() == 0) {
						logger.info("Customer details is Not Exist");
						boolean customer = customerDao.addNewCustomer(customerInput);
						if (customer == false) {
							logger.info("Unable to create customer");
							response.setHttpStatus(HttpStatus.BAD_REQUEST);
							response.setMessage("Account Creation Failed");
							return response;
						} else {
							logger.info("Account Created Success");
							response.setHttpStatus(HttpStatus.ACCEPTED);
							response.setMessage("Account Created Success");
							return response;
						}
					} else {
						logger.info("Existing Customer");
						response.setHttpStatus(HttpStatus.NOT_ACCEPTABLE);
						response.setMessage("Existing Customer");
						return response;
					}
				} else {
					logger.fatal("Database Connection Error");
					response.setHttpStatus(HttpStatus.SERVICE_UNAVAILABLE);
					response.setMessage("Database Connection Error");
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