package com.portal.dto;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class ProductDTO extends BaseProductDTO {

	public ProductDTO() {
		// TODO Auto-generated constructor stub
	}

	@JsonbCreator
	public ProductDTO(@JsonbProperty("code") String code,
			@JsonbProperty("description_product_type") String descriptionType,
			@JsonbProperty("commercial_code") String commercialCode, @JsonbProperty("product_type") String type,
			@JsonbProperty("description") String description, @JsonbProperty("multiple") Integer multiple) {
		super(code, descriptionType, commercialCode, type, description, multiple);
		
		
	}
	
	

}
