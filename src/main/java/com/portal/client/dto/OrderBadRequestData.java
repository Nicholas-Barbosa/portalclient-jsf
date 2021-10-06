package com.portal.client.dto;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class OrderBadRequestData {

	private String errorMessage;

	@JsonbCreator
	public OrderBadRequestData(@JsonbProperty("errorMessage") String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
}
