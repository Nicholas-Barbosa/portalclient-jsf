package com.farawaybr.portal.exception;

import com.farawaybr.portal.vo.Customer404Error;

public class CustomerNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7174807678953476997L;

	private Customer404Error error;

	public CustomerNotFoundException(Customer404Error error) {
		super();
		this.error = error;
	}

	public Customer404Error getError() {
		return error;
	}
}
