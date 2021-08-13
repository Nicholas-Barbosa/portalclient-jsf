package com.portal.client.dto;

import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.vo.Product;

public class ProductPage {

	private List<Product> products;

	@JsonbCreator
	public ProductPage(@JsonbProperty("products") List<Product> products) {
		super();
		this.products = new ArrayList<>(products);
	}

	public List<Product> getProducts() {
		return new ArrayList<>(products);
	}

}
