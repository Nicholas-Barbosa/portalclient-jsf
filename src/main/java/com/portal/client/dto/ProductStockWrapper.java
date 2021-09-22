package com.portal.client.dto;

import java.util.List;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class ProductStockWrapper {

	private List<ProductStock> stock;

	@JsonbCreator
	public ProductStockWrapper(@JsonbProperty("stock") List<ProductStock> stock) {
		super();
		this.stock = stock;
	}

	public List<ProductStock> getStock() {
		return stock;
	}

}
