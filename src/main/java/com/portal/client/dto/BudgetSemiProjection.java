package com.portal.client.dto;

import java.time.LocalDate;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

import com.portal.client.dto.helper.StringToLocalDateParser;

public class BudgetSemiProjection {

	private String code, customerCode, customerStore, customerName;
	private double stValue, liquidValue;
	private float discount;
	private LocalDate createdAt;

	@JsonbCreator
	public BudgetSemiProjection(@JsonbProperty("client_code") String customerCode,
			@JsonbProperty("store") String customerStore, @JsonbProperty("budget_code") String budgetCode,
			@JsonbProperty("creation_date") String createdAt, @JsonbProperty("st_value") double stValue,
			@JsonbProperty("liquid_order_value") double liquidOrderValue,
			@JsonbProperty("client_name") String customerName) {
		this.code = budgetCode;
		this.customerCode = customerCode;
		this.customerStore = customerStore;
		this.customerName = customerName;
		this.stValue = stValue;
		this.liquidValue = liquidOrderValue;
		this.createdAt = StringToLocalDateParser.convert(createdAt);
	}

	public String getCode() {
		return code;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public String getCustomerStore() {
		return customerStore;
	}

	public String getCustomerName() {
		return customerName;
	}

	public double getStValue() {
		return stValue;
	}

	public double getLiquidValue() {
		return liquidValue;
	}

	public float getDiscount() {
		return discount;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}
}
