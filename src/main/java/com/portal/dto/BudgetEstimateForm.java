package com.portal.dto;

import java.util.HashSet;
import java.util.Set;

import javax.json.bind.annotation.JsonbProperty;

public class BudgetEstimateForm {

	@JsonbProperty("client")
	private String client;
	@JsonbProperty("store")
	private String store;
	@JsonbProperty("items")
	private Set<ItemEstimateBudgetForm> items;

	public BudgetEstimateForm(String client, String store, Set<ItemEstimateBudgetForm> items) {
		super();
		this.client = client;
		this.store = store;
		this.items = new HashSet<>(items);
	}

	public String getClient() {
		return client;
	}

	public String getStore() {
		return store;
	}

	public Set<ItemEstimateBudgetForm> getItems() {
		return new HashSet<>(items);
	}

}
