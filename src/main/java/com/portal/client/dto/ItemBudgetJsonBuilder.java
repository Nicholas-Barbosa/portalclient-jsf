package com.portal.client.dto;

import java.math.BigDecimal;

public abstract class ItemBudgetJsonBuilder {

	private BigDecimal budgetGlobalDiscount;
	private BigDecimal lineDiscount;
	private BigDecimal unitStValue;
	private BigDecimal unitValue;
	private BigDecimal unitGrossValue;
	private BigDecimal totalStValue;
	private BigDecimal totalValue;
	private BigDecimal totalGrossValue;
	private int quantity;
	private Product product;

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

	public ItemBudgetJsonBuilder withQuantity(int value) {
		this.quantity = value;
		return this;
	}

	public ItemBudgetJsonBuilder withUnitValue(BigDecimal value) {
		this.unitValue = value;
		return this;
	}

	public ItemBudgetJsonBuilder withProduct(Product value) {
		this.product = value;
		return this;
	}

	public BigDecimal getBudgetGlobalDiscount() {
		return budgetGlobalDiscount;
	}

	public BigDecimal getLineDiscount() {
		return lineDiscount;
	}

	public BigDecimal getUnitStValue() {
		return unitStValue;
	}

	public BigDecimal getUnitValue() {
		return unitValue;
	}

	public BigDecimal getUnitGrossValue() {
		return unitGrossValue;
	}

	public BigDecimal getTotalStValue() {
		return totalStValue;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public BigDecimal getTotalGrossValue() {
		return totalGrossValue;
	}

	public int getQuantity() {
		return quantity;
	}

	public Product getProduct() {
		return product;
	}

	public abstract ItemBudget build();

}
