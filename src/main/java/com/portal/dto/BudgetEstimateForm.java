package com.portal.dto;

import java.util.HashSet;
import java.util.Set;

import javax.json.bind.annotation.JsonbProperty;

public class BudgetEstimateForm {

	@JsonbProperty
	private String client;
	@JsonbProperty
	private String store;
	@JsonbProperty
	private Set<ItemQuoteBudgetForm> items;

	public BudgetEstimateForm(String client, String store, Set<ItemQuoteBudgetForm> items) {
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

	public Set<ItemQuoteBudgetForm> getItems() {
		return new HashSet<>(items);
	}

}
