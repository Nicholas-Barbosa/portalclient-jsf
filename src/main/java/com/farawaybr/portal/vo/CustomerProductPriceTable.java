package com.farawaybr.portal.vo;

import java.util.Set;

public class CustomerProductPriceTable {

	private String code;
	private Set<Product> products;

	public CustomerProductPriceTable() {
		// TODO Auto-generated constructor stub
	}
	
	public CustomerProductPriceTable(String code, Set<Product> products) {
		super();
		this.code = code;
		this.products = products;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}
}
