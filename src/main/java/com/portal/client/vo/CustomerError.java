package com.portal.client.vo;

import javax.json.bind.annotation.JsonbProperty;

public class CustomerError {

	private int errorCode;
	private String errorMessage;

	public int getErrorCode() {
		return errorCode;
	}

	@JsonbProperty("errorCode")
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	@JsonbProperty("errorMessage")
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "CustomerError [errorCode=" + errorCode + ", errorMessage=" + errorMessage + "]";
	}

}
