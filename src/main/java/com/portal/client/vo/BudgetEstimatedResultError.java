package com.portal.client.vo;

public class BudgetEstimatedResultError {

	private int status;
	private boolean okWithItems;
	private ItemError[] itemsWithErrors;

	public BudgetEstimatedResultError(int status, boolean okWithItems, ItemError[] itemsWithErrors) {
		super();
		this.status = status;
		this.okWithItems = okWithItems;
		this.itemsWithErrors = itemsWithErrors;
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

	public static class ItemError {

		private String cause, itemIdentity;

		public ItemError(String cause, String itemIdentity) {
			super();
			this.cause = cause;
			this.itemIdentity = itemIdentity;
		}

		public String getCause() {
			return cause;
		}

		public String getItemIdentity() {
			return itemIdentity;
		}

	}
}
