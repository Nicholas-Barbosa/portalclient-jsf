package com.portal.java.dto;

import javax.json.bind.annotation.JsonbProperty;

public class ProductBudgetFormDTO {

	private int quantity;
	private final String commercialCode;
	private final BaseProductDTO productDTO;

	public ProductBudgetFormDTO(int quantity, String commercialCode, BaseProductDTO productDTO) {
		super();
		this.quantity = quantity;
		this.commercialCode = commercialCode;
		this.productDTO = new BaseProductDTO(productDTO);
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

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BaseProductDTO getProductDTO() {
		return new BaseProductDTO(productDTO);
	}

}
