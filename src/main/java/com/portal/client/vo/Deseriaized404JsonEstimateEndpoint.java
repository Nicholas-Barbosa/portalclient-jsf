package com.portal.client.vo;

import com.portal.client.vo.WrapperProduct404Error.Product404Error;

public class Deseriaized404JsonEstimateEndpoint {

	private Product404Error[] itemErrors;
	private Customer404Error customerError;
	private boolean okWithItems;
	private boolean okWithCustomer;

	public Deseriaized404JsonEstimateEndpoint(Product404Error[] itemErrors, Customer404Error customerError) {
		super();
		this.itemErrors = itemErrors;
		this.customerError = customerError;
		checkBooleanFields();
	}

	public Product404Error[] getItemErrors() {
		return itemErrors;
	}

	public Customer404Error getCustomerError() {
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
