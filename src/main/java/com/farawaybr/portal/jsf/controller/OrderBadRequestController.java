package com.farawaybr.portal.jsf.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.farawaybr.portal.dto.OrderBadRequestData;
import com.farawaybr.portal.repository.OrderBadRequestExcpetion;

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
