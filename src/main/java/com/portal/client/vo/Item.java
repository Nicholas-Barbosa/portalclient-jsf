package com.portal.client.vo;

public class Item {

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
}
