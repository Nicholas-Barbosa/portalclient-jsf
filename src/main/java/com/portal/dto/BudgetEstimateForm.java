package com.portal.dto;

import java.util.HashSet;
import java.util.Set;

import javax.json.bind.annotation.JsonbProperty;

public class BudgetEstimateForm {

	private String client;

	private String store;

	private Set<ItemFormDTO> itemsForm;

	public BudgetEstimateForm(String client, String store, Set<ItemFormDTO> items) {
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
	public Set<ItemFormDTO> getItemsForm() {
		return new HashSet<>(itemsForm);
	}

}
