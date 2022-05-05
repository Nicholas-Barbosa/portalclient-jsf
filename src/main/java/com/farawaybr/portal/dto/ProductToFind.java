package com.farawaybr.portal.dto;

import javax.json.bind.annotation.JsonbProperty;

public class ProductToFind {

	private String productCode;
	private int quantity;

	public ProductToFind(String productCode, int quantity) {
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

		public ProductToFind build() {
			return new ProductToFind(productCode, quantity);
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
		ProductToFind other = (ProductToFind) obj;
		if (productCode == null) {
			if (other.productCode != null)
				return false;
		} else if (!productCode.equals(other.productCode))
			return false;
		return true;
	}

}
