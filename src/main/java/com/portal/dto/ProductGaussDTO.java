package com.portal.dto;

import javax.json.bind.annotation.JsonbProperty;

public class ProductGaussDTO {

	@JsonbProperty
	private String code;
	@JsonbProperty("description_product_type")
	private String descriptionType;
	@JsonbProperty("commercial_code")
	private String commercialCode;
	@JsonbProperty("product_type")
	private String type;
	@JsonbProperty
	private String description;

	public ProductGaussDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProductGaussDTO(String code, String descriptionType, String commercialCode, String type,
			String description) {
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
