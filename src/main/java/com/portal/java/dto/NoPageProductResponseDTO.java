package com.portal.java.dto;

import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class NoPageProductResponseDTO {

	private List<ProductDTO> products;

	@JsonbCreator
	public NoPageProductResponseDTO(@JsonbProperty("products") List<ProductDTO> products) {
		super();
		this.products = new ArrayList<>(products);
	}

	public List<ProductDTO> getProducts() {
		return new ArrayList<>(products);
	}

}
