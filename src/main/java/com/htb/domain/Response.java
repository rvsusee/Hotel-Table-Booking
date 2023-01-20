package com.htb.domain;

import org.json.JSONObject;

public class Response {

	private String httpStatus;
	private String message;
	private String object;

	public Response() {
	}

	public Response(String httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	public Response(String httpStatus, String message, String object) {
		this.httpStatus = httpStatus;
		this.message = message;
		this.object = object;
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

	public String getObject() {
		return object;
	}

	public void setObject(JSONObject jsonObject) {
		this.object = jsonObject.toString();
	}

}
