package com.portal.client.dto;

import java.util.Set;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;

public class BudgetToSaveJsonSerializable {

	@JsonbTransient
	private BaseBudget budget;

	public BudgetToSaveJsonSerializable(BaseBudget budget) {
		this.budget = budget;
	}

	public static BudgetToSaveJsonSerializable of(BaseBudget baseBudget) {
		return new BudgetToSaveJsonSerializable(baseBudget);
	}

	@JsonbProperty("client_code")
	public String getCustomerCode() {
		return budget.getCustomerOnOrder().getCode();
	}

	@JsonbProperty("store")
	public String getCustomerStore() {
		return budget.getCustomerOnOrder().getStore();
	}

	@JsonbProperty("representative_order")
	public String getRepresentativeOrder() {
		return budget.getRepresentativeOrder();
	}

	@JsonbProperty("client_order")
	public String getCustomerOrder() {
		return budget.getCustomerOrder();
	}

	@JsonbProperty("message")
	public String getMessage() {
		return budget.getMessage();
	}

	@SuppressWarnings("unchecked")
	@JsonbProperty("items")
	public Set<ItemBudgetToSaveJsonSerializable> getItems() {
		return (Set<ItemBudgetToSaveJsonSerializable>) budget.getItems();
	}
}
