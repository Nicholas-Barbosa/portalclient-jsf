package com.farawaybr.portal.vo;

import com.farawaybr.portal.vo.WrapperProductBatchSearchEndpointError.ProductBatchSearchEndpointError;

public class Deseriaized404JsonEstimateEndpoint {

	private ProductBatchSearchEndpointError[] itemErrors;
	private Customer404Error customerError;
	private boolean okWithItems;
	private boolean okWithCustomer;

	public Deseriaized404JsonEstimateEndpoint(ProductBatchSearchEndpointError[] itemErrors, Customer404Error customerError) {
		super();
		this.itemErrors = itemErrors;
		this.customerError = customerError;
		checkBooleanFields();
	}

	public ProductBatchSearchEndpointError[] getItemErrors() {
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
