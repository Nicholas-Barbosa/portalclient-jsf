package com.portal.dto;

import java.util.Collection;
import java.util.List;

import javax.json.bind.annotation.JsonbProperty;

public class CustomerResponseGaussDTO implements PageResponse<CustomerGaussDTO> {

	@JsonbProperty("total_items")
	private Integer totalItems;

	@JsonbProperty("total_page")
	private Integer totalPages;

	@JsonbProperty("page_size")
	private Integer pageSize;

	@JsonbProperty
	private Integer page;

	@JsonbProperty("client")
	private List<CustomerGaussDTO> clients;

	public CustomerResponseGaussDTO() {
		// TODO Auto-generated constructor stub
	}

	public CustomerResponseGaussDTO(Integer totalItems, Integer totalPages, Integer pageSize, Integer page,
			List<CustomerGaussDTO> clients) {
		super();
		this.totalItems = totalItems;
		this.totalPages = totalPages;
		this.pageSize = pageSize;
		this.page = page;
		this.clients = clients;
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
