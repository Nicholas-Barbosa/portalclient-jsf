package com.portal.client.vo;

import javax.json.bind.annotation.JsonbTransient;

public class ItemBudget {

	@JsonbTransient
	private final Product product;
	@JsonbTransient
	private ItemBudgetValue values;

	public ItemBudget(Product product, ItemBudgetValue itemPrice) {
		super();
		this.product = product;
		this.values = itemPrice;
	}

	public ItemBudget() {
		this(null, null);
	}

	public ItemBudget(ItemBudget item) {
		this(item.product, item.values);
	}

	public Product getProduct() {
		return product;
	}

	public ItemBudgetValue getValue() {
		return values;
	}
	@JsonbTransient
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
		ItemBudget other = (ItemBudget) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

}
