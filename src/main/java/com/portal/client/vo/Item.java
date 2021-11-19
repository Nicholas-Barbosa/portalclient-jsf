package com.portal.client.vo;

import java.io.Serializable;

public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3280139406134360476L;

	private Order order;

	private Product product;

	public Item() {
		// TODO Auto-generated constructor stub
	}

	public Item(Product product) {
		super();
		this.product = product;
	}

	public Item(Order order, Product product) {
		super();
		this.order = order;
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
