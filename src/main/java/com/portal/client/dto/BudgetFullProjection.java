package com.portal.client.dto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.vo.Budget;
import com.portal.client.vo.Item;

public class BudgetFullProjection extends Budget {

	@JsonbCreator
	public static BudgetFullProjection ofJsonb(@JsonbProperty("client_code") String customerCode,
			@JsonbProperty("store") String customerStore, @JsonbProperty("budget") String code,
			@JsonbProperty("liquid_order_value") BigDecimal liquidValue,
			@JsonbProperty("representative_order") String representativeOrder, @JsonbProperty("message") String message,
			@JsonbProperty("gross_order_value") BigDecimal grossValue,
			@JsonbProperty("items") Set<ItemBudgetProjection> items) {

		CustomerOnOrder customer = new CustomerOnOrder(customerCode, customerStore, null, null, null, null, null, null,
				null);
		return new BudgetFullProjection(customer, code, null, liquidValue, grossValue,
				items.stream().map(BudgetFullProjection::castItem).collect(Collectors.toSet()), message);

	}

	public BudgetFullProjection(CustomerOnOrder customer, String code, BigDecimal stValue, BigDecimal liquidValue,
			BigDecimal grossValue, Set<Item> items, String message) {
		super(code, customer, grossValue, liquidValue, stValue, BigDecimal.ZERO, items, message);
	}

	private static Item castItem(ItemBudgetProjection item) {
		return (Item) item;
	}
}
