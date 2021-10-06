package com.portal.client.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.portal.client.dto.OrderBadRequestData;
import com.portal.client.repository.OrderBadRequestExcpetion;

@RequestScoped
@Named
public class OrderBadRequestController {

	private OrderBadRequestData error;

	@Inject
	public OrderBadRequestController(HttpSession session) {
		error = ((OrderBadRequestExcpetion) session.getAttribute("orderBadRequestException")).getError();
		session.removeAttribute("orderBadRequestException");
	}

	public String getError() {
		return error.getErrorMessage();
	}
}
