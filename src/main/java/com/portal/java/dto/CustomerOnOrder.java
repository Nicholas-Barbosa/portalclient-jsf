package com.portal.java.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.portal.java.pojo.Customer;

public class CustomerOnOrder {

	@NotEmpty
	private Customer customer;
	@NotEmpty
	private CustomerType type;
	@Size(max = 250)
	private String message;

	public CustomerOnOrder() {
		// TODO Auto-generated constructor stub
	}

	

	
	public CustomerOnOrder(@NotEmpty Customer customer, @NotEmpty CustomerType type) {
		super();
		this.customer = customer;
		this.type = type;
	}




	public CustomerOnOrder(@NotEmpty Customer customer, @NotEmpty CustomerType type, @Size(max = 250) String message) {
		super();
		this.customer = customer;
		this.type = type;
		this.message = message;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
