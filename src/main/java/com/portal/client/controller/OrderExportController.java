package com.portal.client.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@RequestScoped
@Named
public class OrderExportController {

	private boolean order;

	private HttpSession httpSession;

	@Inject
	public OrderExportController(HttpSession httpSession) {
		super();
		this.httpSession = httpSession;
	}

	public void export() {
		
	}
	public boolean isOrder() {
		return order;
	}

	public void setOrder(boolean order) {
		this.order = order;
	}

}
