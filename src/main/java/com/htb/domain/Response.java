package com.htb.domain;

import org.json.JSONObject;

public class Response {

	private String httpStatus;
	private String message;
	private String responseBody;

	public Response() {
	}

	public Response(String httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	public Response(String httpStatus, String message, String responseBody) {
		this.httpStatus = httpStatus;
		this.message = message;
		this.responseBody = responseBody;
	}

	public String getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(JSONObject responseBody) {
		this.responseBody = responseBody.toString();
	}

}
