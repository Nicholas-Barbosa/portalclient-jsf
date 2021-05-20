package com.portal.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class ProductQueryPageDTO implements Page<ProductQueryDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6631658017840393068L;

	private int totalItems;

	private int totalPages;

	private int pageSize;

	private int page;

	private List<ProductQueryDTO> products;

	public ProductQueryPageDTO() {
		// TODO Auto-generated constructor stub
	}

	@JsonbCreator
	public ProductQueryPageDTO(@JsonbProperty("total_items") Integer totalItems,
			@JsonbProperty("total_page") Integer totalPages, @JsonbProperty("page_size") Integer pageSize,
			@JsonbProperty("page") Integer page, @JsonbProperty("products") List<ProductQueryDTO> products) {
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
	public Collection<ProductQueryDTO> getContent() {
		return new ArrayList<>(products);
	}

}
