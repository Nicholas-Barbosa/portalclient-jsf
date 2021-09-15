package com.portal.client.vo.builder;

import java.math.BigDecimal;

import com.portal.client.dto.ProductValue;
import com.portal.client.vo.ItemValue;

public class ItemValueBuilder implements ContractItemValueBuilder {

	private BigDecimal budgetGlobalDiscount;
	private BigDecimal lineDiscount;
	private BigDecimal unitStValue;
	private BigDecimal unitValue;
	private BigDecimal unitGrossValue;
	private BigDecimal totalStValue;
	private BigDecimal totalValue;
	private BigDecimal totalGrossValue;
	private int quantity;
	private Integer multiple;

	public static ItemValueBuilder getInstance() {
		return new ItemValueBuilder();
	}

	@Override
	public ItemValueBuilder withLineDiscount(BigDecimal value) {
		this.lineDiscount = value;
		return this;
	}

	@Override
	public ItemValueBuilder withUnitStValue(BigDecimal value) {
		this.unitStValue = value;
		return this;
	}

	@Override
	public ItemValueBuilder withUnitGrossValue(BigDecimal value) {
		this.unitGrossValue = value;
		return this;
	}

	@Override
	public ItemValueBuilder withTotalStValue(BigDecimal value) {
		this.totalStValue = value;
		return this;
	}

	@Override
	public ItemValueBuilder withTotalValue(BigDecimal value) {
		this.totalValue = value;
		return this;
	}

	@Override
	public ItemValueBuilder withTotalGrossValue(BigDecimal value) {
		this.totalGrossValue = value;
		return this;
	}

	@Override
	public ItemValueBuilder withQuantity(int value) {
		this.quantity = value;
		return this;
	}

	@Override
	public ContractItemValueBuilder withMultiple(Integer value) {
		this.multiple = value;
		return this;
	}

	@Override
	public ItemValueBuilder withUnitValue(BigDecimal value) {
		this.unitValue = value;
		return this;
	}

	@Override
	public ContractItemValueBuilder withGlobalDiscount(BigDecimal value) {
		this.budgetGlobalDiscount = value;
		return this;
	}

	@Override
	public ContractItemValueBuilder withProductValue(ProductValue vl) {
		this.withUnitGrossValue(vl.getUnitGrossValue()).withUnitStValue(vl.getUnitStValue())
				.withUnitValue(vl.getUnitValue());
		return this;
	}

	@Override
	public ItemValue build() {
		return new ItemValue(quantity, multiple, budgetGlobalDiscount, lineDiscount, unitStValue, unitValue,
				unitGrossValue, totalStValue, totalValue, totalGrossValue);
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

}
