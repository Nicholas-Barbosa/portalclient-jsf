package com.portal.client.dto;

import java.util.Set;

import javax.json.bind.annotation.JsonbProperty;

public class FormToEstimateBudget {

	private String customerCode, store;
	private Set<ItemToFindPrice> items;

	public FormToEstimateBudget(String customerCode, String store, Set<ItemToFindPrice> items) {
		super();
		this.customerCode = customerCode;
		this.store = store;
		this.items = items;
	}

	@JsonbProperty("client")
	public String getCustomerCode() {
		return customerCode;
	}

	@JsonbProperty("store")
	public String getStore() {
		return store;
	}

	@JsonbProperty("items")
	public Set<ItemToFindPrice> getItems() {
		return items;
	}
}
