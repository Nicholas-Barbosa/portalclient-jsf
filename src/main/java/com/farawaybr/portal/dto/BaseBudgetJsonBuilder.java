package com.farawaybr.portal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.farawaybr.portal.vo.Item;

public abstract class BaseBudgetJsonBuilder implements BudgetBuilder {

	private String idCode;
	private String customerCode;
	private String customerStore;
	private BigDecimal grossValue;
	private BigDecimal liquidValue;
	private BigDecimal stValue;
	private List<Item> items;
	private String message;
	private LocalDate createdAt;

	public BaseBudgetJsonBuilder withId(String id) {
		this.idCode = id;
		return this;
	}

	public BaseBudgetJsonBuilder withCustomerCode(String code) {
		this.customerCode = code;
		return this;
	}

	public BaseBudgetJsonBuilder withCustomerStore(String store) {
		this.customerStore = store;
		return this;
	}

	public BaseBudgetJsonBuilder withGrossValue(BigDecimal grossValue) {
		this.grossValue = grossValue;
		return this;
	}

	public BaseBudgetJsonBuilder withLiquidValue(BigDecimal value) {
		this.liquidValue = value;
		return this;
	}

	public BaseBudgetJsonBuilder withStValue(BigDecimal value) {
		this.stValue = value;
		return this;
	}

	public BaseBudgetJsonBuilder withItems(List<Item> items) {
		this.items = new ArrayList<>(items);
		return this;
	}

	public BaseBudgetJsonBuilder withMessage(String message) {
		this.message = message;
		return this;
	}

	public BaseBudgetJsonBuilder withCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	public String getIdCode() {
		return idCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public String getCustomerStore() {
		return customerStore;
	}

	public BigDecimal getGrossValue() {
		return grossValue;
	}

	public BigDecimal getLiquidValue() {
		return liquidValue;
	}

	public BigDecimal getStValue() {
		return stValue;
	}

	public List<Item> getItems() {
		return items;
	}

	public String getMessage() {
		return message;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

}
