package com.portal.client.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.dto.helper.StringToDateParser;
import com.portal.client.vo.Item;
import com.portal.client.vo.Order;

public class OrderFullProjection extends Order {

	

	public OrderFullProjection(String code, String customerNumOrder, String repNumOrder,
			CustomerOnOrder customerOnOrder, BigDecimal grossValue, BigDecimal liquidValue, BigDecimal stValue,
			BigDecimal globalDiscount, String message, List<Item> items, LocalDate createdAt) {
		super(code, customerNumOrder, repNumOrder, customerOnOrder, grossValue, liquidValue, stValue, globalDiscount, message,
				items, createdAt);
		// TODO Auto-generated constructor stub
	}

	@JsonbCreator
	public static OrderFullProjection ofJsonb(@JsonbProperty("order") String code, @JsonbProperty("client_code") String customerCode,
			@JsonbProperty("store") String customerStore, @JsonbProperty("liquid_order_value") BigDecimal value,
			@JsonbProperty("gross_order_value") BigDecimal grossValue, @JsonbProperty("creation_date") String createdAt,
			@JsonbProperty("items") List<ItemOrderFullProjection> items) {

		CustomerOnOrder customer = new CustomerOnOrder(customerCode, customerStore, null, null, null, null, null, null, null);
		return new OrderFullProjection(code, null, null, customer, grossValue, value, null, null, null,
				items.stream().map(i -> (Item) i).collect(Collectors.toList()), StringToDateParser.convert(createdAt));
	}
}
