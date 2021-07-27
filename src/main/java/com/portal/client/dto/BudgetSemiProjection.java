package com.portal.client.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class BudgetSemiProjection extends BaseBudgetProjection{

	
	private final LocalDate createdAt;


	@JsonbCreator
	public BudgetSemiProjection(@JsonbProperty("client_code") String customerCode, @JsonbProperty("store") String customerStore,
			@JsonbProperty("budget_code") String budgetCode, @JsonbProperty("creation_date") String createdAt,
			@JsonbProperty("st_value") BigDecimal stValue,
			@JsonbProperty("liquid_order_value") BigDecimal liquidOrderValue) {
		super(customerCode,customerStore,budgetCode,stValue,liquidOrderValue);
		this.createdAt = this.convertToLdt(createdAt);
		
	}

	private LocalDate convertToLdt(String dateInString) {
		return LocalDateTime.parse(dateInString.substring(0, dateInString.lastIndexOf("-")),
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")).toLocalDate();
	}


	public LocalDate getCreatedAt() {
		return createdAt;
	}



}
