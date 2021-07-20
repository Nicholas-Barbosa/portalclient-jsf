package com.portal.client.dto;

import java.math.BigDecimal;

public class Item {

	private final Product product;
	private ItemValues values;

	public Item(BigDecimal budgetGlobalDiscount, BigDecimal lineDiscount, Product product, ItemValues itemPrice) {
		super();
		this.product = product;
		this.values = itemPrice;
	}

	public Item() {
		this(BigDecimal.ZERO, BigDecimal.ZERO, null, null);
	}

	
	public Product getProduct() {
		return product;
	}

	public ItemValues getValues() {
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
		Item other = (Item) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

}
