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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.htb.dao.BookingDao;
import com.htb.dao.CustomerDao;
import com.htb.domain.BookingDetails;
import com.htb.domain.Customer;
import com.htb.domain.Response;

@RestController
public class Controller {

	@Autowired
	CustomerDao customerDao;

	@Autowired
	BookingDao bookingDao;

	@GetMapping(value = "customerLogin", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Response customerLogin(@RequestBody Customer customerInput) {
		System.out.println("Customer Login API");
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
				} else {
					response.setHttpStatus(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
					response.setMessage("Something Wrong");
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
		} catch (Exception e) {
			response.setHttpStatus(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
			System.out.println("Exception" + e.getLocalizedMessage());
			response.setMessage("Something Wrong");
			return response;
		}
	}


//	get booking by Id
	@GetMapping(value = "getBkDetailsById", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response getBookingDetailsByID(@RequestBody BookingDetails bookingDetails) throws Exception {
		Response response = new Response();
		if (bookingDetails == null || bookingDetails.getId() == 0) {
			response.setHttpStatus(HttpStatus.BAD_REQUEST.toString());
			response.setMessage("Please Enter Booking Details");
			return response;
		} else if (bookingDetails.getCustomer() == null || bookingDetails.getCustomer().getId() == 0) {
			response.setHttpStatus(HttpStatus.BAD_REQUEST.toString());
			response.setMessage("Please Login");
			return response;
		} else {
			bookingDetails = bookingDao.getBookingById(bookingDetails);
			if (bookingDetails != null) {
				response.setHttpStatus(HttpStatus.FOUND.toString());
				response.setMessage("Booking Details Found");
				response.setResponseBody(new JSONObject(bookingDetails));
				return response;
			} else {
				response.setHttpStatus(HttpStatus.NOT_FOUND.toString());
				response.setMessage("Booking Details Not Found");
				return response;
			}
		}
	}

//	get last booking by customer details
	@GetMapping(value = "getLastBk", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response getLastBooking(@RequestBody Customer customer) throws Exception {
		System.out.println("Get Last Booking Details API");
		Response response = new Response();

		if (customer == null || customer.getId() == 0) {
			System.out.println("Custmer Details not Found");
			response.setHttpStatus(HttpStatus.BAD_REQUEST.toString());
			response.setMessage("Please Login");
			return response;
		} else {
			List<BookingDetails> bookingDetails = bookingDao.getLastBookingID(customer);
			if (bookingDetails == null) {
				response.setHttpStatus(HttpStatus.BAD_REQUEST.toString());
				response.setMessage("Booking Details Not Found");
				return response;
			} else if (bookingDetails.size() > 0) {
				response.setHttpStatus(HttpStatus.FOUND.toString());
				response.setMessage("Last Booking Details Found");
				response.setResponseBody(new JSONObject(bookingDetails));
				return response;
			} else {
				response.setHttpStatus(HttpStatus.NOT_FOUND.toString());
				response.setMessage("Booking Details Not Found");
				return response;
			}
		}
	}

	@DeleteMapping(value = "cancelBookingById", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response deleteBooking(@RequestBody BookingDetails bookingDetails) throws Exception {
		Response response = new Response();
		if (bookingDetails == null) {
			response.setHttpStatus(HttpStatus.BAD_REQUEST.toString());
			response.setMessage("Please Enter Booking Details");
			return response;
		} else {
			if (bookingDetails.getCustomer() == null || bookingDetails.getCustomer().getId() == 0) {
				response.setHttpStatus(HttpStatus.BAD_REQUEST.toString());
				response.setMessage("Please Login");
				return response;
			} else {
				if (bookingDao.cancelBookingById(bookingDetails)) {
					response.setHttpStatus(HttpStatus.ACCEPTED.toString());
					response.setMessage("Booking Cancel Successfully");
					return response;
				} else {
					response.setHttpStatus(HttpStatus.NOT_ACCEPTABLE.toString());
					response.setMessage("Unable to Cancel Booking");
					return response;
				}
			}
		}
	}

	@PostMapping(value = "newBooking", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response newBooking(@RequestBody BookingDetails bookingDetails) throws Exception {
		Response response = new Response();
		if (bookingDetails == null) {
			response.setHttpStatus(HttpStatus.BAD_REQUEST.toString());
			response.setMessage("Please Enter Booking Details");
			return response;
		} else if (bookingDetails.getCustomer() == null || bookingDetails.getCustomer().getId() == 0) {
			response.setHttpStatus(HttpStatus.BAD_REQUEST.toString());
			response.setMessage("Please Login");
			return response;
		} else {
			bookingDetails = bookingDao.newBooking(bookingDetails);
			if (bookingDetails != null) {
				response.setHttpStatus(HttpStatus.ACCEPTED.toString());
				response.setMessage("Booking Successfully");
				response.setResponseBody(new JSONObject(bookingDetails));
				return response;
			} else {
				response.setHttpStatus(HttpStatus.NOT_FOUND.toString());
				response.setMessage("Unable to book");
				return response;
			}
		}
	}
}