package com.portal.dto;

import javax.json.bind.annotation.JsonbProperty;

public class ItemFormDTO {

	private String commercialCode;
	private int quantity;

	public ItemFormDTO(String commercialCode, int quantity) {
		this.commercialCode = commercialCode;
		this.quantity = quantity;
	}

	@JsonbProperty("product_code")
	public String getCommercialCode() {
		// TODO Auto-generated method stub
		return commercialCode;
	}

	@JsonbProperty
	public int getQuantity() {
		// TODO Auto-generated method stub
		return quantity;
	}

	public void changeQuantity(int quantity) {
		this.quantity = quantity;

	}
}
