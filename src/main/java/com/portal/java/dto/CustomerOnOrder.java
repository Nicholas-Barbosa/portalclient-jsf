package com.portal.java.dto;

public class CustomerOnOrder {

	private Customer customer;
	private CustomerType type;

	public CustomerOnOrder(Customer customer, CustomerType type) {
		super();
		this.customer = customer;
		this.type = type;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public CustomerType getType() {
		return type;
	}

	public void setType(CustomerType type) {
		this.type = type;
	}

	public static enum CustomerType {
		NORMAL, PROSPECT
	}
}
