package com.portal.client.dto;

import java.util.Set;
import java.util.stream.Collectors;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;

import com.portal.client.vo.CustomerOnOrder;
import com.portal.client.vo.Order;

public class OrderToPersist {

	@JsonbTransient
	private Order order;

	@JsonbTransient
	private CustomerOnOrder customer;

	public OrderToPersist(Order order) {
		super();
		this.order = order;
		this.customer = order.getCustomerOnOrder();
	}

	@JsonbProperty("client_code")
	public String getCustomerCode() {
		return order.getCustomerOnOrder().getCode();
	}

	@JsonbProperty("store")
	public String getCustomerStore() {
		return order.getCustomerOnOrder().getStore();
	}

	@JsonbProperty("message")
	public String getMessage() {
		return order.getMessage() == null ? "null" : order.getMessage();

	}

	@JsonbProperty("representative_order")
	public String getRepresentativeOrder() {
		return order.getRepNumOrder();

	}

	@JsonbProperty("client_order")
	public String getCustomerOrder() {
		return order.getCustomerNumOrder();

	}

	@JsonbProperty("items")
	public Set<ItemOrderToSave> getItems() {
		return order.getItems().stream().map(ItemOrderToSave::new).collect(Collectors.toSet());
	}

	public static OrderToPersist of(Order order2) {
		return new OrderToPersist(order2);
	}
}
