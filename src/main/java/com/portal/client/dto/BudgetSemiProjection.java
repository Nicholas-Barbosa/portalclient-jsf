package com.portal.client.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.vo.Budget;

public class BudgetSemiProjection extends BaseBudgetJsonBuilder {

	@JsonbCreator
	public BudgetSemiProjection(@JsonbProperty("client_code") String customerCode,
			@JsonbProperty("store") String customerStore, @JsonbProperty("budget_code") String budgetCode,
			@JsonbProperty("creation_date") String createdAt, @JsonbProperty("st_value") BigDecimal stValue,
			@JsonbProperty("liquid_order_value") BigDecimal liquidOrderValue) {
		super.withCustomerCode(customerCode).withCustomerStore("store").withId(budgetCode).withStValue(stValue)
				.withLiquidValue(liquidOrderValue).withCreatedAt(convertToLdt(createdAt));
	}

	private LocalDate convertToLdt(String dateInString) {
		return LocalDate.parse(dateInString.substring(0, dateInString.lastIndexOf("T")),
				DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	@Override
	public Budget build() {
		CustomerOnOrder customer = new CustomerOnOrder(super.getCustomerCode(), super.getCustomerStore(), null, null,
				null, null, null, null, null);
		Budget budget = new Budget(super.getIdCode(), null, null, customer, null, super.getLiquidValue(),
				super.getStValue(), null, null, null, super.getCreatedAt());
		return budget;
	}

}
