package com.portal.client.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class BudgetList {

	private final String customerCode;
	private final String customerStore;
	private final String budgetCode;
	private final LocalDateTime createdAt;
	private final BigDecimal stValue;

	@JsonbCreator
	public BudgetList(@JsonbProperty("cliente_code") String customerCode,
			@JsonbProperty("store") String customerStore, @JsonbProperty("budget_code") String budgetCode,
			@JsonbProperty("creation_date") String createdAt, @JsonbProperty("st_value") BigDecimal stValue) {
		super();
		this.customerCode = customerCode;
		this.customerStore = customerStore;
		this.budgetCode = budgetCode;
		this.createdAt = this.convertToLdt(createdAt);
		this.stValue = stValue;
	}

	private LocalDateTime convertToLdt(String dateInString) {
		return LocalDateTime.parse(dateInString.substring(0, dateInString.lastIndexOf("-")),
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public BigDecimal getStValue() {
		return stValue;
	}

}
