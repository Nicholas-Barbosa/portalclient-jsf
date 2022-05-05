package com.farawaybr.portal.jsf.controller.show;

import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.farawaybr.portal.repository.OrderBadRequestExcpetion;
import com.farawaybr.portal.util.jsf.FacesUtils;

@RequestScoped
@Named
public class OrderBadRequestShowController implements ShowController<OrderBadRequestExcpetion> {

	@Inject
	private HttpSession session;

	@Override
	public void show(OrderBadRequestExcpetion p) {
		session.setAttribute("orderBadRequestException", p);
		FacesUtils.openViewOnDialog(Map.of("responsive", true, "modal", true), "/faces/order/orderBadRequest");
	}

}
