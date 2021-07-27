package com.portal.client.dto;

import java.util.List;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class NoPageCustomerResponseDTO {

	private List<Customer> clients;

	@JsonbCreator
	public NoPageCustomerResponseDTO(@JsonbProperty("client") List<Customer> clients) {
		super();
		this.clients = clients;
	}

	public List<Customer> getClients() {
		return clients;
	}
}
