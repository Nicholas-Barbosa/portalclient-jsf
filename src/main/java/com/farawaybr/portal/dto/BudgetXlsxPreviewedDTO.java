package com.farawaybr.portal.dto;

import java.util.Collections;
import java.util.Set;

public class BudgetXlsxPreviewedDTO {

	private String customerName;
	private String customerStore;
	private Set<BudgetProductXlsxDTO> products;

	public BudgetXlsxPreviewedDTO() {
		// TODO Auto-generated constructor stub
	}

	public BudgetXlsxPreviewedDTO(String customerName, String customerStore) {
		super();
		this.customerName = customerName;
		this.customerStore = customerStore;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getCustomerStore() {
		return customerStore;
	}

	public Set<BudgetProductXlsxDTO> getProducts() {
		return Collections.unmodifiableSet(products);
	}

	@Override
	public String toString() {
		return "BudgetXlsxPreviewedDTO [customerCode=" + customerName + ", customerStore=" + customerStore
				+ ", products=" + products + "]";
	}

	public static class BudgetProductXlsxDTO {
		private String commercialCode;
		private int quantity;

		public BudgetProductXlsxDTO(String commercialCode, int quantity) {
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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((commercialCode == null) ? 0 : commercialCode.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BudgetProductXlsxDTO other = (BudgetProductXlsxDTO) obj;
			if (commercialCode == null) {
				if (other.commercialCode != null)
					return false;
			} else if (!commercialCode.equals(other.commercialCode))
				return false;
			return true;
		}

	}
	
	
	
}
