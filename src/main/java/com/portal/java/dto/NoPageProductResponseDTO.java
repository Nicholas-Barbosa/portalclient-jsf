package com.portal.java.dto;

import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class NoPageProductResponseDTO {

	private List<Product> products;

	@JsonbCreator
	public NoPageProductResponseDTO(@JsonbProperty("products") List<Product> products) {
		super();
		this.products = new ArrayList<>(products);
	}

	public List<Product> getProducts() {
		return new ArrayList<>(products);
	}

}
