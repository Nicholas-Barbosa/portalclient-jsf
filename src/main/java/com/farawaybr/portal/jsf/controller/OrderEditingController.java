package com.farawaybr.portal.jsf.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.farawaybr.portal.service.crud.OrderCrudService;
import com.farawaybr.portal.util.jsf.FacesUtils;
import com.farawaybr.portal.vo.Order;

@ViewScoped
@Named
public class OrderEditingController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4879653013176069012L;

	@Inject
	private OrderCrudService orderService;

	private String orderID;

	private Order order;

	public void find() {
		orderService.findByCode(orderID).ifPresentOrElse(o -> {
			order = o;
		}, () -> {
			FacesUtils.error(null, "Pedido não encontrado", null, null);
		});
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public Order getOrder() {
		return order;
	}

}
