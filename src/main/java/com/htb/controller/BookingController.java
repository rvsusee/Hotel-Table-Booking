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
import com.htb.domain.BookingDetails;
import com.htb.domain.Customer;
import com.htb.domain.Response;

@RestController
@RequestMapping("/booking/")
public class BookingController {

	@Autowired
	BookingDao bookingDao;

//	get booking by Id
	@GetMapping(value = "getBookingDetailsById", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response getBookingDetailsById(@RequestBody BookingDetails bookingDetails) throws Exception {
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
	@GetMapping(value = "getLastBookingByCustomerID", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response getLastBookingByCustomerID(@RequestBody Customer customer) throws Exception {
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
	public @ResponseBody Response cancelBookingById(@RequestBody BookingDetails bookingDetails) throws Exception {
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

	@PostMapping(value = "updateBookingById", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response updateBooking(@RequestBody BookingDetails bookingDetails) throws Exception {
		Response response = new Response();
		if (bookingDetails == null) {
			response.setHttpStatus(HttpStatus.BAD_REQUEST.toString());
			response.setMessage("");
			return response;
		} else if (bookingDetails.getId() == 0) {
			response.setHttpStatus(HttpStatus.BAD_REQUEST.toString());
			response.setMessage("Please Enter Booking Number");
			return response;
		} else if (bookingDetails.getCustomer() == null || bookingDetails.getCustomer().getId() == 0) {
			response.setHttpStatus(HttpStatus.BAD_REQUEST.toString());
			response.setMessage("Please Login");
			return response;
		} else {
			bookingDetails = bookingDao.updateBooking(bookingDetails);
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