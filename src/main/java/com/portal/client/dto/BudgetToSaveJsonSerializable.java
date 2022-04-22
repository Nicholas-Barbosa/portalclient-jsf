package com.portal.client.dto;

import java.util.Set;
import java.util.stream.Collectors;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;

import com.portal.client.vo.Budget;

public class BudgetToSaveJsonSerializable {

	@JsonbTransient
	private Budget budget;

	public BudgetToSaveJsonSerializable(Budget budget) {
		this.budget = budget;
	}

	public static BudgetToSaveJsonSerializable of(Budget baseBudget) {
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
		return budget.getRepNumOrder();
	}

	@JsonbProperty("client_order")
	public String getCustomerOrder() {
		return budget.getCustomerNumOrder();
	}

	@JsonbProperty("message")
	public String getMessage() {
		return budget.getMessage();
	}

	@JsonbProperty("items")
	public Set<ItemBudgetToSaveJsonSerializable> getItems() {
		return budget.getItems().stream().map(ItemBudgetToSaveJsonSerializable::of).collect(Collectors.toSet());
	}

	public Budget getBudget() {
		return budget;
	}
}
