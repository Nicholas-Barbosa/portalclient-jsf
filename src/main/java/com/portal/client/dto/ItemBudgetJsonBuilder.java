package com.portal.client.dto;

import java.math.BigDecimal;

public abstract class ItemBudgetJsonBuilder {

	protected BigDecimal budgetGlobalDiscount;
	protected BigDecimal lineDiscount;
	protected BigDecimal unitStValue;
	protected BigDecimal unitValue;
	protected BigDecimal unitGrossValue;
	protected BigDecimal totalStValue;
	protected BigDecimal totalValue;
	protected BigDecimal totalGrossValue;

	protected String productCode;
	protected String commercialCode;
	protected int quantity;

	public ItemBudgetJsonBuilder withGlobalDiscount(BigDecimal value) {
		this.budgetGlobalDiscount = value;
		return this;
	}

	public ItemBudgetJsonBuilder withLineDiscount(BigDecimal value) {
		this.lineDiscount = value;
		return this;
	}

	public ItemBudgetJsonBuilder withUnitStValue(BigDecimal value) {
		this.unitStValue = value;
		return this;
	}

	public ItemBudgetJsonBuilder withUnitGrossValue(BigDecimal value) {
		this.unitGrossValue = value;
		return this;
	}

	public ItemBudgetJsonBuilder withTotalStValue(BigDecimal value) {
		this.totalStValue = value;
		return this;
	}

	public ItemBudgetJsonBuilder withTotalValue(BigDecimal value) {
		this.totalValue = value;
		return this;
	}

	public ItemBudgetJsonBuilder withTotalGrossValue(BigDecimal value) {
		this.totalGrossValue = value;
		return this;
	}

	public ItemBudgetJsonBuilder withProductCode(String value) {
		this.productCode = value;
		return this;
	}

	public ItemBudgetJsonBuilder withCommercialCode(String value) {
		this.commercialCode = value;
		return this;
	}

	public ItemBudgetJsonBuilder withQuantity(int value) {
		this.quantity = value;
		return this;
	}

	public ItemBudgetJsonBuilder withUnitValue(BigDecimal value) {
		this.unitValue = value;
		return this;
	}

	
	public abstract ItemBudget build();

}
