package com.portal.dto;

import javax.json.bind.annotation.JsonbProperty;

public class CustomerBudgetForm {

	@JsonbProperty
	private String name;
	@JsonbProperty
	private String client;
	@JsonbProperty
	private Integer store;
	
	public String getName() {
		return name;
	}
	public String getClient() {
		return client;
	}
	public Integer getStore() {
		return store;
	}
	
	
}
