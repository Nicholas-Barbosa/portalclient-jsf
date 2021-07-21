package com.portal.client.dto;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class BudgetResponse {

	private final String code;

	@JsonbCreator
	public BudgetResponse(@JsonbProperty("budget") String code) {
		super();
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
