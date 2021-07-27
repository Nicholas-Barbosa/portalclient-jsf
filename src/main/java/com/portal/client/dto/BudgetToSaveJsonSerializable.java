package com.portal.client.dto;

import java.util.Set;

import javax.json.bind.annotation.JsonbProperty;

public class BudgetToSaveJsonSerializable extends BaseBudget {

	private String representativeOrder;
	private String customerOrder;

	public BudgetToSaveJsonSerializable(BaseBudget budget, CustomerRepresentativeOrderForm ordersForm) {
		super(budget);
		this.representativeOrder = ordersForm.getRepresentativeOrder();
		this.customerOrder = ordersForm.getCustomerOrder();
	}

	@JsonbProperty("client_code")
	public String getCustomerCode() {
		return super.getCustomerOnOrder().getCode();
	}

	@JsonbProperty("store")
	public String getCustomerStore() {
		return super.getCustomerOnOrder().getStore();
	}

	@JsonbProperty("representative_order")
	public String getRepresentativeOrder() {
		return representativeOrder;
	}

	public void setRepresentativeOrder(String representativeOrder) {
		this.representativeOrder = representativeOrder;
	}

	@JsonbProperty("client_order")
	public String getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(String customerOrder) {
		this.customerOrder = customerOrder;
	}

	@JsonbProperty("message")
	public String getMessage() {
		return super.getCustomerOnOrder().getMessage();
	}

	@SuppressWarnings("unchecked")
	@JsonbProperty("items")
	public Set<ItemBudgetToSaveJsonSerializable> getItems() {
		return (Set<ItemBudgetToSaveJsonSerializable>) super.getItems();
	}
}
