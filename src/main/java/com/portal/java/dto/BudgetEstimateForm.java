package com.portal.java.dto;

import java.util.HashSet;
import java.util.Set;

import javax.json.bind.annotation.JsonbProperty;

public class BudgetEstimateForm {

	private String client;

	private String store;

	private Set<ProductBudgetFormDTO> itemsForm;

	public BudgetEstimateForm(String client, String store, Set<ProductBudgetFormDTO> items) {
		this.client = client;
		this.store = store;
		this.itemsForm = new HashSet<>(items);
	}

	@JsonbProperty("client")
	public String getClient() {
		return client;
	}

	@JsonbProperty("store")
	public String getStore() {
		return store;
	}

	@JsonbProperty("items")
	public Set<ProductBudgetFormDTO> getItemsForm() {
		return itemsForm;
	}

}
