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
		Response response = new Response();

		try {
			if (Long.toString(customerInput.getMobileNumber()).length() != 10) {
				response.setHttpStatus(HttpStatus.NOT_ACCEPTABLE.toString());
				response.setMessage("Please Enter 10 digit Mobile number");
				return response;
			} else {
				List<Customer> customerList = customerDao.customerLogin(customerInput);
				if (customerList == null) {
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
//				check if the customer is exist or not
				List<Customer> customerList = customerDao.customerLogin(customerInput);
				if (customerList == null) {
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

//	
//	// get booking details by id
//		@GetMapping(value = "getBooking")
//		public String getBookingById(@RequestParam Map<String, String> inputs) throws Exception {
//			return bookingDao.getBookingById(inputs);
//		}
//

//		get last booking details
	@GetMapping(value = "getLastBooking", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response getLastBooking(@RequestBody Customer customer) throws Exception {
		Response response = new Response();

		if (customer == null || customer.getId() == 0) {
			response.setHttpStatus(HttpStatus.BAD_REQUEST.toString());
			response.setMessage("Please Login");
			return response;
		} else {
			List<BookingDetails> bookingDetails = bookingDao.getLastBookingID(customer, 1);
			if (bookingDetails == null) {
				if (bookingDetails.size() > 0) {
					response.setHttpStatus(HttpStatus.FOUND.toString());
					response.setMessage("Last Booking Details Found");
					response.setResponseBody(new JSONObject(bookingDetails));
					return response;

				} else {
					response.setHttpStatus(HttpStatus.NOT_FOUND.toString());
					response.setMessage("Booking Details Not Found");
					return response;
				}
			} else {
				response.setHttpStatus(HttpStatus.SERVICE_UNAVAILABLE.toString());
				response.setMessage("Something Wrong");
				return response;
			}
		}

	}

//		Cancel booking by Id
	@GetMapping(value = "cancelBooking", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response deleteBooking(@RequestBody BookingDetails bookingDetails) throws Exception {
		Response response = new Response();

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