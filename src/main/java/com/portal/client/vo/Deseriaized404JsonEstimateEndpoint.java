package com.portal.client.vo;

import com.portal.client.vo.WrapperItemError.ItemError;

public class Deseriaized404JsonEstimateEndpoint {

	private ItemError[] itemErrors;
	private CustomerError customerError;
	private boolean okWithItems;
	private boolean okWithCustomer;

	public Deseriaized404JsonEstimateEndpoint(ItemError[] itemErrors, CustomerError customerError) {
		super();
		this.itemErrors = itemErrors;
		this.customerError = customerError;
		checkBooleanFields();
	}

	public ItemError[] getItemErrors() {
		return itemErrors;
	}

	public CustomerError getCustomerError() {
		return customerError;
	}

	public boolean isOkWithItems() {
		return okWithItems;
	}

	public boolean isOkWithCustomer() {
		return okWithCustomer;
	}

	private final void checkBooleanFields() {
		if (itemErrors == null || itemErrors.length == 0)
			this.okWithItems = true;
		else
			this.okWithItems = false;

		if (customerError == null)
			this.okWithCustomer = true;
		else
			this.okWithCustomer = false;
	}

}
