package com.portal.client.vo;

import com.portal.client.vo.WrapperItem404Error.Item404Error;

public class BudgetEstimatedResult404Error {

	private int status;
	private boolean okWithItems;
	private boolean okWithCustomer;
	private Item404Error[] errorsWithItems;
	private Customer404Error customerError;

	public BudgetEstimatedResult404Error(int status, boolean okWithItems, boolean okWithCustomer,
			Item404Error[] itemsWithErrors, Customer404Error customerError) {
		super();
		this.status = status;
		this.okWithItems = okWithItems;
		this.okWithCustomer = okWithCustomer;
		this.errorsWithItems = itemsWithErrors;
		this.customerError = customerError;
	}

	public int getStatus() {
		return status;
	}

	public boolean isOkWithItems() {
		return okWithItems;
	}

	public Item404Error[] getItemsWithErrors() {
		return errorsWithItems;
	}

	public boolean isOkWithCustomer() {
		return okWithCustomer;
	}

	public Customer404Error getCustomerError() {
		return customerError;
	}
}
