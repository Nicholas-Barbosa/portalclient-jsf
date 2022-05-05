package com.farawaybr.portal.dto;

import java.util.Collection;

import com.farawaybr.portal.vo.Order;
import com.farawaybr.portal.vo.Page;

public class OrderListPage implements Page<Order>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5456186130946148103L;

	
	@Override
	public int getPageSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int totalPages() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int totalItems() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<Order> getContent() {
		// TODO Auto-generated method stub
		return null;
	}

}
