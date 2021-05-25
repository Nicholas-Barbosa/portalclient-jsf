package com.portal.dto;

import javax.json.bind.annotation.JsonbProperty;

public class ItemFormDTO extends BaseProductDTO {

	private int quantity;

	public ItemFormDTO(String commercialCode, String description, int multiple, int quantity) {
		super(null, null, commercialCode, null, description, multiple);
		this.quantity = quantity;
	}

	@JsonbProperty("product_code")
	public String getCommercialCode() {
		// TODO Auto-generated method stub
		return super.getCommercialCode();
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
