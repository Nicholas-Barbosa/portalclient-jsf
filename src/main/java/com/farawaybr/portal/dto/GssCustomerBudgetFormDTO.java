package com.farawaybr.portal.dto;

public class GssCustomerBudgetFormDTO {

	private String cliente;
	private String loja;

	public GssCustomerBudgetFormDTO(String cliente, String loja) {
		super();
		this.cliente = cliente;
		this.loja = loja;
	}

	public String getCliente() {
		return cliente;
	}

	public String getLoja() {
		return loja;
	}

}
