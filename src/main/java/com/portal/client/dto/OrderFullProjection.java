package com.portal.client.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.dto.helper.StringToDateParser;
import com.portal.client.vo.Item;
import com.portal.client.vo.Order;

public class OrderFullProjection extends Order {

	@JsonbCreator
	public static Order ofJsonb(@JsonbProperty("order") String code, @JsonbProperty("client_code") String customerCode,
			@JsonbProperty("store") String customerStore, @JsonbProperty("liquid_order_value") BigDecimal value,
			@JsonbProperty("gross_order_value") BigDecimal grossValue, @JsonbProperty("creation_date") String createdAt,
			@JsonbProperty("items") List<ItemOrderFullProjection> items) {

		return new Order(code, null, null, null, grossValue, value, null, null, null,
				items.stream().map(i -> (Item) i).collect(Collectors.toList()), StringToDateParser.convert(createdAt));
	}
}
