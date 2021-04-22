package com.portal.dto;

import java.util.List;

import javax.json.bind.annotation.JsonbProperty;

public class ProductsResponseGaussDTO {

	@JsonbProperty("total_items")
	private Integer totalItems;
	@JsonbProperty("total_pages")
	private Integer totalPages;
	@JsonbProperty("page_size")
	private Integer pageSize;
	@JsonbProperty
	private Integer page;

	@JsonbProperty
	private List<ProductGaussDTO> products;

	public ProductsResponseGaussDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProductsResponseGaussDTO(Integer totalItems, Integer totalPages, Integer pageSize, Integer page,
			List<ProductGaussDTO> products) {
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

	public List<ProductGaussDTO> getProducts() {
		return products;
	}

}
