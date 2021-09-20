package com.portal.client.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.dto.helper.StringToDateParser;
import com.portal.client.vo.Budget;
import com.portal.client.vo.Item;

public class BudgetFullProjection extends Budget {

	@JsonbCreator
	public static BudgetFullProjection ofJsonb(@JsonbProperty("client_code") String customerCode,
			@JsonbProperty("store") String customerStore, @JsonbProperty("budget") String code,
			@JsonbProperty("liquid_order_value") BigDecimal liquidValue,
			@JsonbProperty("client_order") String customerNumOrder,
			@JsonbProperty("representative_order") String representativeOrder, @JsonbProperty("message") String message,
			@JsonbProperty("gross_order_value") BigDecimal grossValue,
			@JsonbProperty("items") Set<ItemBudgetProjection> items,
			@JsonbProperty("discount") BigDecimal globalDiscount,@JsonbProperty("creation_date") String createdAt) {

		CustomerOnOrder customer = new CustomerOnOrder(customerCode, customerStore, null, null, null, null, null, null,
				null);
		return new BudgetFullProjection(customerNumOrder, representativeOrder, customer, code, null, liquidValue,
				grossValue, items.stream().map(BudgetFullProjection::castItem).collect(Collectors.toList()), message,
				globalDiscount, StringToDateParser.convert(createdAt));

	}

	public BudgetFullProjection(String customerNumOrder, String repNumOrder, CustomerOnOrder customer, String code,
			BigDecimal stValue, BigDecimal liquidValue, BigDecimal grossValue, List<Item> items, String message,
			BigDecimal globalDiscount, LocalDate createdAt) {
		super(code, customerNumOrder, repNumOrder, customer, grossValue, liquidValue, stValue, globalDiscount, items,
				message, createdAt);
	}

	private static Item castItem(ItemBudgetProjection item) {
		return (Item) item;
	}
}
