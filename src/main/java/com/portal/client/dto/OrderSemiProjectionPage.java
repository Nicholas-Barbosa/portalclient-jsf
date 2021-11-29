package com.portal.client.dto;

import java.util.Collection;
import java.util.Set;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.vo.Page;

public class OrderSemiProjectionPage implements Page<OrderSemiProjection> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 814551700799637298L;
	private int pageSize, page, totalPages, totalItems;
	private Set<OrderSemiProjection> orders;
	
	
	
	@JsonbCreator
	public OrderSemiProjectionPage(@JsonbProperty("page_size") int pageSize, @JsonbProperty("page") int page,
			@JsonbProperty("total_page") int totalPages, @JsonbProperty("total_items") int totalItems,@JsonbProperty("order") Set<OrderSemiProjection>orders) {
		super();
		this.pageSize = pageSize;
		this.page = page;
		this.totalPages = totalPages;
		this.totalItems = totalItems;
		this.orders = orders;
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
	public Collection<OrderSemiProjection> getContent() {
		// TODO Auto-generated method stub
		return orders;
	}

	public Set<OrderSemiProjection> getOrders() {
		return orders;
	}

}
