package com.portal.client.vo;

import java.io.Serializable;

public class Item extends Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3280139406134360476L;

	private Order order;
	private String customerOrder;

	public Item(Product product) {
		super(product);
	}

	public Item(Order order, Product product) {
		super(product);
		this.order = order;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(String customerOrder) {
		this.customerOrder = customerOrder;
	}
}
