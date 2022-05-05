package com.farawaybr.portal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.farawaybr.portal.dto.helper.StringToLocalDateParser;
import com.farawaybr.portal.vo.Budget;
import com.farawaybr.portal.vo.CustomerOnOrder;
import com.farawaybr.portal.vo.Item;

public class BudgetFullProjection extends Budget {

	@JsonbCreator
	public static BudgetFullProjection ofJsonb(@JsonbProperty("client_code") String customerCode,
			@JsonbProperty("store") String customerStore, @JsonbProperty("budget") String code,
			@JsonbProperty("liquid_order_value") BigDecimal liquidValue,
			@JsonbProperty("client_order") String customerNumOrder,
			@JsonbProperty("representative_order") String representativeOrder, @JsonbProperty("message") String message,
			@JsonbProperty("gross_order_value") BigDecimal grossValue,
			@JsonbProperty("items") Set<ItemBudgetProjectionDTO> items,
			@JsonbProperty("discount") BigDecimal globalDiscount, @JsonbProperty("creation_date") String createdAt) {
		CustomerOnOrder customer = new CustomerOnOrder(customerCode, customerStore, null, null, null, null, null, null,
				null);
		return new BudgetFullProjection(customerNumOrder, representativeOrder, customer, code, grossValue.subtract(liquidValue), liquidValue,
				grossValue, items.stream().map(BudgetFullProjection::castItem).collect(Collectors.toList()), message,
				globalDiscount, StringToLocalDateParser.convert(createdAt));

	}

	public BudgetFullProjection(String customerNumOrder, String repNumOrder, CustomerOnOrder customer, String code,
			BigDecimal stValue, BigDecimal liquidValue, BigDecimal grossValue, List<Item> items, String message,
			BigDecimal globalDiscount, LocalDate createdAt) {
		super(code, customerNumOrder, repNumOrder, customer, grossValue, liquidValue, stValue, globalDiscount, items,
				message, createdAt, null);

	}

	private static Item castItem(ItemBudgetProjectionDTO item) {
		return (Item) item;
	}

}
