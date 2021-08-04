package com.portal.client.dto;

import java.math.BigDecimal;
import java.util.Set;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.dto.CustomerOnOrder.CustomerType;

public class BudgetFullProjection extends BaseBudget {

	@JsonbCreator
	public static BudgetFullProjection ofJsonb(@JsonbProperty("client_code") String customerCode,
			@JsonbProperty("store") String customerStore, @JsonbProperty("budget") String code,
			@JsonbProperty("liquid_order_value") BigDecimal liquidValue,
			@JsonbProperty("representative_order") String representativeOrder, @JsonbProperty("message") String message,
			@JsonbProperty("gross_order_value") BigDecimal grossValue,
			@JsonbProperty("items") Set<ItemBudgetProjection> items) {

		Customer customer = new Customer(customerCode, customerStore, null, null, null, null, null, null, null);

		CustomerOnOrder customerOnOrder = new CustomerOnOrder(customer, CustomerType.NORMAL, message);
		return new BudgetFullProjection(customerOnOrder, code, null, liquidValue, grossValue, items);

	}

	public BudgetFullProjection(CustomerOnOrder customer, String code, BigDecimal stValue, BigDecimal liquidValue,
			BigDecimal grossValue, Set<ItemBudgetProjection> items) {
		super(code, customer, grossValue, liquidValue, stValue, grossValue, items);
	}

}