package com.portal.dto;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class ProductQueryDTO extends ProductDTO {

	public ProductQueryDTO() {
		// TODO Auto-generated constructor stub
	}

	@JsonbCreator
	public ProductQueryDTO(@JsonbProperty("code") String code,
			@JsonbProperty("description_product_type") String descriptionType,
			@JsonbProperty("commercial_code") String commercialCode, @JsonbProperty("product_type") String type,
			@JsonbProperty("description") String description) {
		super(code, descriptionType, commercialCode, type, description, null,0,null);

	}

}
