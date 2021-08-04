package com.portal.client.vo;

import com.portal.client.vo.WrapperItemError.ItemError;

public class BudgetEstimatedResultError {

	private int status;
	private boolean okWithItems;
	private boolean okWithCustomer;
	private ItemError[] itemsWithErrors;
	private CustomerError customerError;

	public BudgetEstimatedResultError(int status, boolean okWithItems, boolean okWithCustomer,
			ItemError[] itemsWithErrors, CustomerError customerError) {
		super();
		this.status = status;
		this.okWithItems = okWithItems;
		this.okWithCustomer = okWithCustomer;
		this.itemsWithErrors = itemsWithErrors;
		this.customerError = customerError;
	}

	public int getStatus() {
		return status;
	}

	public boolean isOkWithItems() {
		return okWithItems;
	}

	public ItemError[] getItemsWithErrors() {
		return itemsWithErrors;
	}

	public boolean isOkWithCustomer() {
		return okWithCustomer;
	}

	public CustomerError getCustomerError() {
		return customerError;
	}
}
