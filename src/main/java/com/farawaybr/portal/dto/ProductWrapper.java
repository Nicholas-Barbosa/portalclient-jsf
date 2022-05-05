package com.farawaybr.portal.dto;

import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class ProductWrapper {

	private List<ProductSearchDto> products;

	@JsonbCreator
	public ProductWrapper(@JsonbProperty("products") List<ProductSearchDto> products) {
		super();
		this.products = new ArrayList<>(products);
	}

	public List<ProductSearchDto> getProducts() {
		return new ArrayList<>(products);
	}

}
