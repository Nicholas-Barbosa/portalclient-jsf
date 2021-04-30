package com.portal.pojo;

import java.util.Set;

public class ProductPage {
	
	private Integer totalItems;

	private Integer totalPages;

	private Integer pageSize;

	private Integer page;

	private Set<Product> products;

	public ProductPage(Integer totalItems, Integer totalPages, Integer pageSize, Integer page, Set<Product> products) {
		super();
		this.totalItems = totalItems;
		this.totalPages = totalPages;
		this.pageSize = pageSize;
		this.page = page;
		this.products = products;
	}

	public Integer getTotalItems() {
		return totalItems;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public Integer getPage() {
		return page;
	}

	public Set<Product> getProducts() {
		return products;
	}

}
