package com.portal.dto;

import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class NoPageProductResponseDTO {

	private List<ProductQueryDTO> products;

	@JsonbCreator
	public NoPageProductResponseDTO(@JsonbProperty("products") List<ProductQueryDTO> products) {
		super();
		this.products = new ArrayList<>(products);
	}

	public List<ProductQueryDTO> getProducts() {
		return new ArrayList<>(products);
	}

}
