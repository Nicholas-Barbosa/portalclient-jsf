package com.portal.client.dto;

import java.math.BigDecimal;

public class BaseBudgetProjection {

	private final String customerCode;
	private final String customerStore;
	private final String code;
	private final BigDecimal stValue;
	private final BigDecimal liquidValue;

	public BaseBudgetProjection(String customerCode, String customerStore, String code, BigDecimal stValue, BigDecimal liquidValue) {
		super();
		this.customerCode = customerCode;
		this.customerStore = customerStore;
		this.code = code;
		this.stValue = stValue;
		this.liquidValue = liquidValue;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public String getCustomerStore() {
		return customerStore;
	}

	public String getCode() {
		return code;
	}

	public BigDecimal getStValue() {
		return stValue;
	}

	public BigDecimal getLiquidValue() {
		return liquidValue;
	}

}
