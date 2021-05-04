package com.portal.pojo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ProductPage implements Page<Product> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4300799200414274533L;

	private int totalItems;

	private int totalPages;

	private int pageSize;

	private int page;

	private Set<Product> products;

	public ProductPage(int totalItems, int totalPages, int pageSize, int page, Set<Product> products) {
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
	public Collection<Product> getContent() {
		// TODO Auto-generated method stub
		return new HashSet<>(products);
	}

}
