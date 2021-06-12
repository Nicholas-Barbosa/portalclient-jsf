package com.portal.java.dto;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BudgetImportXlsxDTO {

	private String customerCode;
	private String state;
	private String store;
	private Set<ItemBudgetXlsxDTO> items;

	public BudgetImportXlsxDTO(String customerCode, String state, String store, Set<ItemBudgetXlsxDTO> items) {
		super();
		this.customerCode = customerCode;
		this.state = state;
		this.store = store;
		this.items = new HashSet<>(items);
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public String getState() {
		return state;
	}

	public String getStore() {
		return store;
	}

	public Set<ItemBudgetXlsxDTO> getItems() {
		return Collections.unmodifiableSet(items);
	}

	public static class ItemBudgetXlsxDTO {
		private String commercialCode;
		private int quantity;

		public ItemBudgetXlsxDTO(String commercialCode, int quantity) {
			super();
			this.commercialCode = commercialCode;
			this.quantity = quantity;
		}

		public String getCommercialCode() {
			return commercialCode;
		}

		public int getQuantity() {
			return quantity;
		}

	}

}
