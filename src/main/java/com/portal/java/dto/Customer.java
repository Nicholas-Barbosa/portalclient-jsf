package com.portal.java.dto;

public class Customer {


	private String address;
	private String code;
	private String store;
	private String state;
	private String cgc;
	private String name;
	private String fantasyName;
	private String city;

	public Customer(CustomerDTO customer) {
		this.address = customer.getAddress();
		this.code = customer.getCode();
		this.store = customer.getStore();
		this.state = customer.getState();
		this.cgc = customer.getCgc();
		this.name = customer.getName();
		this.fantasyName = customer.getFantasyName();
		this.city = customer.getCity();
	}

	public Customer(Customer customer) {
		
	}
	public String getAddress() {
		return address;
	}

	public String getCode() {
		return code;
	}

	public String getStore() {
		return store;
	}

	public String getState() {
		return state;
	}

	public String getCgc() {
		return cgc;
	}

	public String getName() {
		return name;
	}

	public String getFantasyName() {
		return fantasyName;
	}

	public String getCity() {
		return city;
	}

}
