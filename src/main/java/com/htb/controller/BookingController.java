package com.htb.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	BookingDao bookingDao;

	Logger logger = LogManager.getLogger("HotelTableBooking");

	@GetMapping(value = "/getBookingDetailsById", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response getBookingDetailsById(@RequestBody BookingDetails bookingDetails) throws Exception {

		Response response = new Response();
		try {
			if (bookingDetails == null || bookingDetails.getId() == 0) {
				logger.warn("Booking Details Validation - Failed");
				response.setHttpStatus(HttpStatus.BAD_REQUEST);
				response.setMessage("Booking Details Vaildation - Failed");
				return response;
			} else if (bookingDetails.getCustomer() == null || bookingDetails.getCustomer().getId() == 0) {
				logger.warn("Customer Login - Failed");
				response.setHttpStatus(HttpStatus.BAD_REQUEST);
				response.setMessage("Login - Failed");
				return response;
			} else {
				logger.info("Booking Details Validation - Success");
				bookingDetails = bookingDao.getBookingById(bookingDetails);
				if (bookingDetails != null) {
					logger.info("Booking Details - Found");
					response.setHttpStatus(HttpStatus.FOUND);
					response.setMessage("Booking Details - Found");
					response.setResponseBody(new JSONObject(bookingDetails));
					return response;
				} else {
					logger.error("Booking Details - Not Found");
					response.setHttpStatus(HttpStatus.NOT_FOUND);
					response.setMessage("Booking Details - Not Found");
					return response;
				}
			}
		} catch (Exception e) {
			logger.fatal("Exception Occured : " + e.getLocalizedMessage());
			response.setHttpStatus(HttpStatus.NOT_FOUND);
			response.setMessage("Exception Occured");
			return response;
		}
	}

	@GetMapping(value = "/getLastBookingByCustomerID", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response getLastBookingByCustomerID(@RequestBody Customer customer) throws Exception {
		logger.info("Get Last Booking Details API");
		Response response = new Response();
		try {
			if (customer == null || customer.getId() == 0) {
				logger.warn("Custmer Details Validation - Failed");
				response.setHttpStatus(HttpStatus.BAD_REQUEST);
				response.setMessage("Login - Failed");
				return response;
			} else {
				List<BookingDetails> bookingDetails = bookingDao.getLastBookingByCustomerID(customer);
				if (bookingDetails == null) {
					logger.info("Database Integration - Failed");
					response.setHttpStatus(HttpStatus.BAD_REQUEST);
					response.setMessage("Database Integration - Failed");
					return response;
				} else if (bookingDetails.size() > 0) {
					logger.info("Booking Details - Found");
					response.setHttpStatus(HttpStatus.FOUND);
					response.setMessage("Booking Details - Found");
					response.setResponseBody(new JSONObject(bookingDetails));
					return response;
				} else {
					logger.info("Booking Details - Not Found");
					response.setHttpStatus(HttpStatus.NOT_FOUND);
					response.setMessage("Booking Details - Not Found");
					return response;
				}
			}
		} catch (Exception e) {
			logger.fatal("Exception Occured : " + e.getLocalizedMessage());
			response.setHttpStatus(HttpStatus.SERVICE_UNAVAILABLE);
			response.setMessage("Exception Occured");
			return response;
		}
	}

	@DeleteMapping(value = "/cancelBookingById", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response cancelBookingById(@RequestBody BookingDetails bookingDetails) throws Exception {
		logger.info("cancelBookingById API");
		Response response = new Response();
		try {
			if (bookingDetails == null) {
				logger.warn("Booking Details Validation - Failed");
				response.setHttpStatus(HttpStatus.BAD_REQUEST);
				response.setMessage("Booking Details Validation - Failed");
				return response;
			} else {
				if (bookingDetails.getCustomer() == null || bookingDetails.getCustomer().getId() == 0) {
					logger.info("Customer Login - Failed");
					response.setHttpStatus(HttpStatus.BAD_REQUEST);
					response.setMessage("Login - Failed");
					return response;
				} else {
					logger.info("Booking Details Validation - Success");
					if (bookingDao.cancelBookingById(bookingDetails)) {
						logger.info("Booking Cancellation - Success");
						response.setHttpStatus(HttpStatus.ACCEPTED);
						response.setMessage("Booking Cancellation - Success");
						return response;
					} else {
						logger.info("Booking Cancellation - Failed");
						response.setHttpStatus(HttpStatus.NOT_ACCEPTABLE);
						response.setMessage("Booking Cancellation - Failed");
						return response;
					}
				}
			}
		} catch (Exception e) {
			logger.fatal("Exception Occured : " + e.getLocalizedMessage());
			response.setHttpStatus(HttpStatus.SERVICE_UNAVAILABLE);
			response.setMessage("Exception Occured");
			return response;
		}
	}

	@PostMapping(value = "/newBooking", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response newBooking(@RequestBody BookingDetails bookingDetails) throws Exception {
		logger.info("newBooking API");
		Response response = new Response();
		try {
			if (bookingDetails == null || bookingDetails.getCustomer() == null
					|| bookingDetails.getCustomer().getId() == 0) {
				logger.warn("Booking Details Validation - Failed");
				response.setHttpStatus(HttpStatus.BAD_REQUEST);
				response.setMessage("Booking Details Validation - Failed");
				return response;
			} else {
				bookingDetails = bookingDao.newBooking(bookingDetails);
				if (bookingDetails != null) {
					logger.info("Booking - Confirmed");
					response.setHttpStatus(HttpStatus.ACCEPTED);
					response.setMessage("Booking - Confirmed");
					response.setResponseBody(new JSONObject(bookingDetails));
					return response;
				} else {
					logger.error("Booking - Failed");
					response.setHttpStatus(HttpStatus.NOT_FOUND);
					response.setMessage("Booking - Failed");
					return response;
				}
			}
		} catch (Exception e) {
			logger.fatal("Exception Occured : " + e.getLocalizedMessage());
			response.setHttpStatus(HttpStatus.SERVICE_UNAVAILABLE);
			response.setMessage("Exception Occured");
			return response;
		}
	}

	@PostMapping(value = "/updateBookingById", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response updateBooking(@RequestBody BookingDetails bookingDetails) throws Exception {
		Response response = new Response();

		try {
			if (bookingDetails == null || bookingDetails.getId() == 0 || bookingDetails.getCustomer() == null
					|| bookingDetails.getCustomer().getId() == 0) {
				logger.info("Booking Details Validation - Failed");
				response.setHttpStatus(HttpStatus.BAD_REQUEST);
				response.setMessage("Booking Details Validatio - Failed");
				return response;
			} else {
				boolean status = bookingDao.updateBooking(bookingDetails);
				if (status == true) {
					logger.info("Update Booking Details - Success");
					response.setHttpStatus(HttpStatus.ACCEPTED);
					response.setMessage("Update - Success");
					response.setResponseBody(new JSONObject(bookingDetails));
					return response;
				} else {
					logger.warn("Update Booking Details - Failed");
					response.setHttpStatus(HttpStatus.NOT_FOUND);
					response.setMessage("Update - Failed");
					return response;
				}
			}
		} catch (Exception e) {
			logger.fatal("Exception Occured : " + e.getLocalizedMessage());
			response.setHttpStatus(HttpStatus.SERVICE_UNAVAILABLE);
			response.setMessage("Exception Occured");
			return response;
		}
	}

}