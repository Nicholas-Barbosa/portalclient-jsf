package com.portal.client.dto;

import java.math.BigDecimal;

public class ItemBudgetJsonBuilder {

	private BigDecimal budgetGlobalDiscount;
	private BigDecimal lineDiscount;
	private BigDecimal unitStValue;
	private BigDecimal unitValue;
	private BigDecimal unitGrossValue;
	private BigDecimal totalStValue;
	private BigDecimal totalValue;
	private BigDecimal totalGrossValue;

	private String productCode;
	private String commercialCode;
	private int quantity;

	public static ItemBudgetJsonBuilder getInstance() {
		return new ItemBudgetJsonBuilder();
	}

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
	
	public ItemBudget build() {
		ProductValue productValue = new ProductValue(unitStValue, unitValue, unitGrossValue);
		Product product = new Product(productCode, commercialCode, null, null, null, null, 0, 0, false, null,
				productValue);
		ItemBudgetValue value = new ItemBudgetValue(quantity, budgetGlobalDiscount, lineDiscount, unitStValue,
				unitValue, unitGrossValue, totalStValue, totalValue, totalGrossValue, productValue);
		return new ItemBudget(product, value);
	}

}
