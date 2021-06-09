package com.portal.java.dto;

import javax.json.bind.annotation.JsonbProperty;

public class ProductBudgetFormDTO extends BaseProductDTO {

	public ProductBudgetFormDTO(BaseProductDTO product) {
		super(product);
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
