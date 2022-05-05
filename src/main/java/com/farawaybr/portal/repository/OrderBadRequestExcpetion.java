package com.farawaybr.portal.repository;

import com.farawaybr.portal.dto.OrderBadRequestData;

public class OrderBadRequestExcpetion extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2171512557925572574L;

	private OrderBadRequestData error;

	public OrderBadRequestExcpetion(OrderBadRequestData error) {
		super();
		this.error = error;
	}

	public OrderBadRequestData getError() {
		return error;
	}
}
