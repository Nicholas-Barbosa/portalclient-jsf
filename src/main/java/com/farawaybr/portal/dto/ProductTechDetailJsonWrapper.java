package com.farawaybr.portal.dto;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class ProductTechDetailJsonWrapper {

	private ProductTechDetailJson[] details;

	@JsonbCreator
	public ProductTechDetailJsonWrapper(@JsonbProperty ProductTechDetailJson[] details) {
		super();
		this.details = details;
	}

	public ProductTechDetailJson[] getDetails() {
		return details;
	}
	
	
}
