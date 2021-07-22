package com.portal.client.dto;

import java.math.BigDecimal;

import com.portal.client.vo.Product;

public class ItemBudgetToSave {

	private final Product product;
	private ItemBudgetToSaveValues values;

	public ItemBudgetToSave(BigDecimal budgetGlobalDiscount, BigDecimal lineDiscount, Product product,
			ItemBudgetToSaveValues itemPrice) {
		super();
		this.product = product;
		this.values = itemPrice;
	}

	public ItemBudgetToSave() {
		this(BigDecimal.ZERO, BigDecimal.ZERO, null, null);
	}

	public Product getProduct() {
		return product;
	}

	public ItemBudgetToSaveValues getValues() {
		return values;
	}

	public String line() {
		return product.getLine();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		ItemBudgetToSave other = (ItemBudgetToSave) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

}
