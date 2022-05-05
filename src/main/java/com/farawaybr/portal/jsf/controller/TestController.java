package com.farawaybr.portal.jsf.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.farawaybr.portal.vo.Customer;

@SessionScoped
@Named
public class TestController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5862573919600147805L;
	private Customer customer = new Customer("keyword", "keyword", "keyword", "keyword", "keyword", "keyword", null,
			null, null, null);

	public Customer getCustomer() {
		return customer;
	}
}
