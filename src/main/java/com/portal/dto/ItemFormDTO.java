package com.portal.dto;

import javax.json.bind.annotation.JsonbProperty;

public class ItemFormDTO extends ProductDTO {

	public ItemFormDTO(String code, String descriptionType, String commercialCode, String description, int quantity) {
		super(code, descriptionType, commercialCode, null, description, null, quantity, null);
	}

	@Override
	@JsonbProperty("product_code")
	public String getCommercialCode() {
		// TODO Auto-generated method stub
		return super.getCommercialCode();
	}

	@Override
	@JsonbProperty
	public int getQuantity() {
		// TODO Auto-generated method stub
		return super.getQuantity();
	}
}
