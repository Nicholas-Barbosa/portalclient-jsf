package com.portal.java.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.java.pojo.Customer;

public class CustomerPageDTO implements Page<Customer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1774399950276987596L;

	private int totalItems;

	private int totalPages;

	private int pageSize;

	private int page;

	private List<Customer> clients;

	public CustomerPageDTO() {
		// TODO Auto-generated constructor stub
	}
	@JsonbCreator
	public CustomerPageDTO(@JsonbProperty("total_items") int totalItems, @JsonbProperty("total_page") int totalPages,
			@JsonbProperty("page_size") int pageSize, @JsonbProperty("page") int page,
			@JsonbProperty("client") List<Customer> clients) {
		super();
		this.totalItems = totalItems;
		this.totalPages = totalPages;
		this.pageSize = pageSize;
		this.page = page;
		this.clients = new ArrayList<>(clients);
	}

	public List<Customer> getClients() {
		return new ArrayList<>(clients);
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
		return clients;
	}
	@Override
	public String toString() {
		return "CustomerPage [totalItems=" + totalItems + ", totalPages=" + totalPages + ", pageSize=" + pageSize
				+ ", page=" + page + ", clients=" + clients + "]";
	}

}
