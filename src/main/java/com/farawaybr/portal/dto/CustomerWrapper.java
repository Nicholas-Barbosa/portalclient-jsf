package com.farawaybr.portal.dto;

import java.util.List;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.farawaybr.portal.vo.Customer;

public class CustomerWrapper {

	private Customer customer;

	@JsonbCreator
	public CustomerWrapper(@JsonbProperty("client") List<CustomerJson> customerJson) {
		super();
		Customer customer = customerJson != null && customerJson.get(0) != null ? customerJson.get(0).getCustomer()
				: null;
		this.customer = customer;
	}

	public Customer getCustomer() {
		return customer;
	}
}
