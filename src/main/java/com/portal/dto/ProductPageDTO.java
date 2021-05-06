package com.portal.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.json.bind.annotation.JsonbProperty;

public class ProductPageDTO implements Page<ProductDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6631658017840393068L;
	@JsonbProperty("total_items")
	private int totalItems;
	@JsonbProperty("total_pages")
	private int totalPages;
	@JsonbProperty("page_size")
	private int pageSize;
	@JsonbProperty
	private int page;

	@JsonbProperty
	private List<ProductDTO> products;

	public ProductPageDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProductPageDTO(Integer totalItems, Integer totalPages, Integer pageSize, Integer page,
			List<ProductDTO> products) {
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
	public Collection<ProductDTO> getContent() {

		return new ArrayList<>(products);
	}

}
