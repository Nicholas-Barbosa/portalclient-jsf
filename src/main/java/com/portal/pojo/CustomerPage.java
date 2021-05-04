package com.portal.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomerPage implements Page<Customer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7129315331224897427L;

	private int totalItems;

	private int totalPages;

	private int pageSize;

	private int page;

	private List<Customer> clients;

	public CustomerPage() {
		// TODO Auto-generated constructor stub
	}

	public CustomerPage(int totalItems, int totalPages, int pageSize, int page, List<Customer> clients) {
		super();
		this.totalItems = totalItems;
		this.totalPages = totalPages;
		this.pageSize = pageSize;
		this.page = page;
		this.clients = clients;
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
	public Collection<Customer> getContent() {
		// TODO Auto-generated method stub
		return new ArrayList<>(clients);
	}

}
