package com.portal.dto;

import javax.json.bind.annotation.JsonbProperty;

public class ErrorGaussDTO {

	@JsonbProperty
	private Integer errorCode;
	@JsonbProperty
	private String errorMessage;
	
	public Integer getErrorCode() {
		return errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	
	
}
