package com.portal.client.vo;

import java.io.Serializable;

public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3280139406134360476L;
	private Product product;
	private ItemValue value;

	public Item() {
		// TODO Auto-generated constructor stub
	}

	public Item(Product product, ItemValue value) {
		super();
		this.product = product;
		this.value = value;
	}

	public Product getProduct() {
		return product;
	}

	public ItemValue getValue() {
		return value;
	}

	public String getLine() {
		return product.getLine();
	}

	public static Item product(Product p) {
		return new Item(p, new ItemValue(p.getValue()));
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
