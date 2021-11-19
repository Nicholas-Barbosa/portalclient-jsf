package com.portal.client.dto;

import java.util.Set;

import javax.json.bind.annotation.JsonbProperty;

public class BatchProductSearchJsonPostForm {

	private String customerCode, store;
	private Set<ProductToFind> items;

	public BatchProductSearchJsonPostForm(String customerCode, String store, Set<ProductToFind> items) {
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
	public Set<ProductToFind> getItems() {
		return items;
	}
}
