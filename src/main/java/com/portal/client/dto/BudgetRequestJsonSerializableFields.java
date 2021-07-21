package com.portal.client.dto;

import javax.json.bind.annotation.JsonbProperty;

public class BudgetRequestJsonSerializableFields {

	private String customerCode;
	private String customerStore;
	private String representativeOrder;
	private String customerOrder;

	@JsonbProperty("client_code")
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	@JsonbProperty("store")
	public String getCustomerStore() {
		return customerStore;
	}

	public void setCustomerStore(String customerStore) {
		this.customerStore = customerStore;
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

}
