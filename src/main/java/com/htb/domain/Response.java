package com.htb.domain;

import org.springframework.http.HttpStatus;

public class Response {

	private String message;
	private HttpStatus httpStatus;
	private Object object;

	public Response() {
	}

	public Response(String message, HttpStatus httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}

	public Response(String message, HttpStatus httpStatus, Object object) {
		this.message = message;
		this.httpStatus = httpStatus;
		this.object = object;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

}
