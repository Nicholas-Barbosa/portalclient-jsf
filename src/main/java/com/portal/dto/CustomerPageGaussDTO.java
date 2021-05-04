package com.portal.dto;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.json.bind.annotation.JsonbProperty;

import com.portal.pojo.CustomerPage;
import com.portal.pojo.Page;

public class CustomerPageGaussDTO implements Page<CustomerGaussDTO> {

	@JsonbProperty("total_items")
	private int totalItems;

	@JsonbProperty("total_page")
	private int totalPages;

	@JsonbProperty("page_size")
	private int pageSize;

	@JsonbProperty
	private int page;

	@JsonbProperty("client")
	private List<CustomerGaussDTO> clients;

	public CustomerPageGaussDTO() {
		// TODO Auto-generated constructor stub
	}

	public CustomerPageGaussDTO(int totalItems, int totalPages, int pageSize, int page,
			List<CustomerGaussDTO> clients) {
		super();
		this.totalItems = totalItems;
		this.totalPages = totalPages;
		this.pageSize = pageSize;
		this.page = page;
		this.clients = clients;
	}

	public CustomerPage toCustomerPage() {
		return new CustomerPage(totalItems, totalPages, pageSize, page, clients.parallelStream()
				.map(CustomerGaussDTO::toCustomer).collect(CopyOnWriteArrayList::new, List::add, List::addAll));
	}

	public List<CustomerGaussDTO> getClients() {
		return clients;
	}

	@Override
	public String toString() {
		return "GssResponseClientsDTO [totalItems=" + totalItems + ", totalPages=" + totalPages + ", pageSize="
				+ pageSize + ", page=" + page + ", clients=" + clients + "]";
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
	public Collection<CustomerGaussDTO> getContent() {
		// TODO Auto-generated method stub
		return clients;
	}

}
