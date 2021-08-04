package com.portal.client.dto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseBudgetJsonBuilder implements BudgetBuilder{

	protected String idCode;
	protected String customerCode;
	protected String customerStore;
	protected BigDecimal grossValue;
	protected BigDecimal liquidValue;
	protected BigDecimal stValue;
	protected Set<ItemBudgetJsonBuilder> items;

	
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

	public BaseBudgetJsonBuilder withItems(Set<? extends ItemBudgetJsonBuilder> items) {
		this.items = new HashSet<>(items);
		return this;
	}

}