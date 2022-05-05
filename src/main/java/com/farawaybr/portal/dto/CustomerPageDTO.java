package com.farawaybr.portal.dto;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.farawaybr.portal.vo.Customer;
import com.farawaybr.portal.vo.Page;

public class CustomerPageDTO implements Page<Customer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1774399950276987596L;

	private int totalItems;

	private int totalPages;

	private int pageSize;

	private int page;

	private List<Customer> customers;

	public CustomerPageDTO() {
		// TODO Auto-generated constructor stub
	}

	@JsonbCreator
	public CustomerPageDTO(@JsonbProperty("total_items") int totalItems, @JsonbProperty("total_page") int totalPages,
			@JsonbProperty("page_size") int pageSize, @JsonbProperty("page") int page,
			@JsonbProperty("client") List<CustomerJson> clients) {
		super();
		this.totalItems = totalItems;
		this.totalPages = totalPages;
		this.pageSize = pageSize;
		this.page = page;
		this.customers = clients.parallelStream().map(CustomerJson::getCustomer).collect(CopyOnWriteArrayList::new,
				List::add, List::addAll);

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
	public String toString() {
		return "CustomerPage [totalItems=" + totalItems + ", totalPages=" + totalPages + ", pageSize=" + pageSize
				+ ", page=" + page + ", clients=" + customers + "]";
	}

	@Override
	public Collection<Customer> getContent() {
		// TODO Auto-generated method stub
		return customers;
	}

}
