package com.portal.dto;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class ProductDTO {

	private String code;

	private String descriptionType;

	private String commercialCode;

	private String type;

	private String description;

	public ProductDTO() {
		// TODO Auto-generated constructor stub
	}

	@JsonbCreator
	public ProductDTO(@JsonbProperty("code") String code, @JsonbProperty("description_product_type") String descriptionType,
			@JsonbProperty("commercial_code") String commercialCode, @JsonbProperty("product_type") String type,
			@JsonbProperty("description") String description) {
		super();
		this.code = code;
		this.descriptionType = descriptionType;
		this.commercialCode = commercialCode;
		this.type = type;
		this.description = description;

	}

	public String getCode() {
		return code;
	}

	public String getDescriptionType() {
		return descriptionType;
	}

	public String getCommercialCode() {
		return commercialCode;
	}

	public String getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

}
