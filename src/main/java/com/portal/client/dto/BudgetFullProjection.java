package com.portal.client.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class BudgetFullProjection extends BaseBudgetProjection {

	private final String representativeOrder;
	private final String message;
	private final BigDecimal grossValue;
	private final Set<ItemBudgetProjection> items;

	@JsonbCreator
	public static BudgetFullProjection ofJsonb(@JsonbProperty("client_code") String customerCode,
			@JsonbProperty("store") String customerStore, @JsonbProperty("budget") String code,
			@JsonbProperty("liquid_order_value") BigDecimal liquidValue,
			@JsonbProperty("representative_order") String representativeOrder, @JsonbProperty("message") String message,
			@JsonbProperty("gross_order_value") BigDecimal grossValue,
			@JsonbProperty("items") Set<ItemBudgetProjection> items) {
		return new BudgetFullProjection(customerCode, customerStore, code, grossValue, liquidValue, representativeOrder,
				message, grossValue, items);
		
	}

	public BudgetFullProjection(String customerCode, String customerStore, String code, BigDecimal stValue,
			BigDecimal liquidValue, String representativeOrder, String message, BigDecimal grossValue,
			Set<ItemBudgetProjection> items) {
		super(customerCode, customerStore, code, stValue, liquidValue);
		this.representativeOrder = representativeOrder;
		this.message = message;
		this.grossValue = grossValue;
		this.items = items;
	}

	public String getRepresentativeOrder() {
		return representativeOrder;
	}

	public String getMessage() {
		return message;
	}

	public BigDecimal getGrossValue() {
		return grossValue;
	}

	public Set<ItemBudgetProjection> getItems() {
		return new HashSet<>(items);
	}

}
