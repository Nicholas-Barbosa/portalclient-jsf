package com.portal.dto;

import java.util.List;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class NoPageCustomerResponseDTO {

	private List<CustomerDTO> clients;

	@JsonbCreator
	public NoPageCustomerResponseDTO(@JsonbProperty("client") List<CustomerDTO> clients) {
		super();
		this.clients = clients;
	}

	public List<CustomerDTO> getClients() {
		return clients;
	}
}
