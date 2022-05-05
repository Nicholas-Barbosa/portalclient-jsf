package com.farawaybr.portal.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.farawaybr.portal.dto.helper.StringToLocalDateParser;
import com.farawaybr.portal.vo.CustomerOnOrder;
import com.farawaybr.portal.vo.Item;
import com.farawaybr.portal.vo.Order;

public class OrderFullProjection extends Order implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7111841709509384056L;

	public OrderFullProjection(String code,String repNumOrder,
			CustomerOnOrder customerOnOrder, BigDecimal grossValue, BigDecimal liquidValue, BigDecimal stValue,
			BigDecimal globalDiscount, String message, List<Item> items, LocalDate createdAt) {
		super(code, null, repNumOrder, customerOnOrder, grossValue, liquidValue, stValue, globalDiscount,
				message, items, createdAt, null, null);
		// TODO Auto-generated constructor stub
	}

	@JsonbCreator
	public static OrderFullProjection ofJsonb(@JsonbProperty("order") String code,
			@JsonbProperty("client_code") String customerCode, @JsonbProperty("store") String customerStore,
			@JsonbProperty("liquid_order_value") BigDecimal value,
			@JsonbProperty("gross_order_value") BigDecimal grossValue, @JsonbProperty("creation_date") String createdAt,
			@JsonbProperty("items") List<ItemOrderFullProjection> items) {

		CustomerOnOrder customer = new CustomerOnOrder(customerCode, customerStore, null, null, null, null, null, null,
				null);
		return new OrderFullProjection(code, null, customer, grossValue, value, grossValue.subtract(value), null,
				null, items.stream().map(i -> (Item) i).collect(Collectors.toList()),
				StringToLocalDateParser.convert(createdAt));
	}
}
