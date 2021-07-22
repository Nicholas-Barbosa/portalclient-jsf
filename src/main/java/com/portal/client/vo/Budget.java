package com.portal.client.vo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class Budget {

	private final String customerCode;
	private final String customerStore;
	private final String budgetCode;
	private final LocalDate createdAt;
	private final BigDecimal stValue;
	private final BigDecimal liquidOrderValue;

	@JsonbCreator
	public Budget(@JsonbProperty("client_code") String customerCode, @JsonbProperty("store") String customerStore,
			@JsonbProperty("budget_code") String budgetCode, @JsonbProperty("creation_date") String createdAt,
			@JsonbProperty("st_value") BigDecimal stValue,
			@JsonbProperty("liquid_order_value") BigDecimal liquidOrderValue) {
		super();
		this.customerCode = customerCode;
		this.customerStore = customerStore;
		this.budgetCode = budgetCode;
		this.createdAt = this.convertToLdt(createdAt);
		this.stValue = stValue;
		this.liquidOrderValue = liquidOrderValue;
	}

	private LocalDate convertToLdt(String dateInString) {
		return LocalDateTime.parse(dateInString.substring(0, dateInString.lastIndexOf("-")),
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")).toLocalDate();
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public String getCustomerStore() {
		return customerStore;
	}

	public String getBudgetCode() {
		return budgetCode;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public BigDecimal getStValue() {
		return stValue;
	}

	public BigDecimal getLiquidOrderValue() {
		return liquidOrderValue;
	}

}
