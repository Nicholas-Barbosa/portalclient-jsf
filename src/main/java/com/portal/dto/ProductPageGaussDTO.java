package com.portal.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.json.bind.annotation.JsonbProperty;

import com.portal.pojo.Page;
import com.portal.pojo.ProductPage;

public class ProductPageGaussDTO implements Page<ProductGaussDTO> {

	@JsonbProperty("total_items")
	private int totalItems;
	@JsonbProperty("total_pages")
	private int totalPages;
	@JsonbProperty("page_size")
	private int pageSize;
	@JsonbProperty
	private int page;

	@JsonbProperty
	private List<ProductGaussDTO> products;

	public ProductPageGaussDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProductPageGaussDTO(Integer totalItems, Integer totalPages, Integer pageSize, Integer page,
			List<ProductGaussDTO> products) {
		super();
		this.totalItems = totalItems;
		this.totalPages = totalPages;
		this.pageSize = pageSize;
		this.page = page;
		this.products = products;
	}

	public ProductPage toProduct() {
		return new ProductPage(totalItems, page, pageSize, page, products.parallelStream().map(p -> p.toProduct())
				.collect(ConcurrentSkipListSet::new, Set::add, Set::addAll));
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
