package com.portal.dto;

import javax.json.bind.annotation.JsonbProperty;

public class ItemFormDTO extends BaseProductDTO {

	public ItemFormDTO(String commercialCode, String description, Integer multiple, int quantity) {
		super(commercialCode, description, multiple, quantity);

	}

	@JsonbProperty("product_code")
	public String getCommercialCode() {
		// TODO Auto-generated method stub
		return super.getCommercialCode();
	}

	@JsonbProperty
	public int getQuantity() {
		// TODO Auto-generated method stub
		return super.getQuantity();
	}

}
