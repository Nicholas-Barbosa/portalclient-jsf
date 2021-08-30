package com.portal.client.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
		return LocalDateTime.parse(dateInString.substring(0, dateInString.lastIndexOf("-")),
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")).toLocalDate();
	}

	@Override
	public Budget build() {
		CustomerOnOrder customer = new CustomerOnOrder(super.getCustomerCode(), super.getCustomerStore(), null, null,
				null, null, null, null, null);
		Budget budget = new Budget(super.getIdCode(), customer, null, super.getLiquidValue(),
				super.getStValue(), null, null, null);
		return budget;
	}

}
