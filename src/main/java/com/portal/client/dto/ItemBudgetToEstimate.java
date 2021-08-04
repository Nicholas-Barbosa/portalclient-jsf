package com.portal.client.dto;

import javax.json.bind.annotation.JsonbProperty;

public class ItemBudgetToEstimate {

	private String productCode;
	private int quantity;

	public ItemBudgetToEstimate(String productCode, int quantity) {
		super();
		this.productCode = productCode;
		this.quantity = quantity;
	}

	@JsonbProperty("product_code")
	public String getProductCode() {
		return productCode;
	}

	@JsonbProperty("quantity")
	public int getQuantity() {
		return quantity;
	}

	public static class ItemBudgetToEstimateb {
		private String productCode;
		private int quantity;

		public static ItemBudgetToEstimateb getInstance() {
			return new ItemBudgetToEstimateb();
		}

		public void withCode(String code) {
			this.productCode = code;
		}

		public void withQuantity(int quantity) {
			this.quantity = quantity;
		}

		public ItemBudgetToEstimate build() {
			return new ItemBudgetToEstimate(productCode, quantity);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productCode == null) ? 0 : productCode.hashCode());
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
		ItemBudgetToEstimate other = (ItemBudgetToEstimate) obj;
		if (productCode == null) {
			if (other.productCode != null)
				return false;
		} else if (!productCode.equals(other.productCode))
			return false;
		return true;
	}

}
