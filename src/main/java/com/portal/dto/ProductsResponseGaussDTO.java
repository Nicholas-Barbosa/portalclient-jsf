package com.portal.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.json.bind.annotation.JsonbProperty;

public class ProductsResponseGaussDTO implements PageResponse<ProductGaussDTO> {

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

	@Override
	public int getPageSize() {
		// TODO Auto-generated method stub
		return pageSize;
	}

	@Override
	public int getPage() {
		// TODO Auto-generated method stub
		return page;
	}

	@Override
	public int totalPages() {
		// TODO Auto-generated method stub
		return totalPages;
	}

	@Override
	public int totalItems() {
		// TODO Auto-generated method stub
		return totalItems;
	}

	@Override
	public Collection<ProductGaussDTO> getContent() {

		return new ArrayList<>(products);
	}

}
