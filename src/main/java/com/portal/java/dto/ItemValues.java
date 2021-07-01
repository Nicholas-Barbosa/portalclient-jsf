package com.portal.java.dto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ItemValues {
	private int quantity;
	private BigDecimal unitStValue;
	private BigDecimal unitValue;
	private BigDecimal unitGrossValue;

	private BigDecimal totalStValue;
	private BigDecimal totalValue;
	private BigDecimal totalGrossValue;
	private final Map<String, BigDecimal> values = new ConcurrentHashMap<>();

	public ItemValues(int quantity, BigDecimal unitStValue, BigDecimal unitValue, BigDecimal unitGrossValue,
			BigDecimal totalStValue, BigDecimal totalValue, BigDecimal totalGrossValue) {
		super();
		this.quantity = quantity;
		this.unitStValue = unitStValue;
		this.unitValue = unitValue;
		this.unitGrossValue = unitGrossValue;
		this.totalStValue = totalStValue;
		this.totalValue = totalValue;
		this.totalGrossValue = totalGrossValue;
		values.put("unitStValue", unitStValue);
		values.put("unitValue", unitValue);
		values.put("unitGrossValue", unitGrossValue);
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitStValue() {
		return unitStValue;
	}

	public void setUnitStValue(BigDecimal unitStValue) {
		this.unitStValue = unitStValue;
	}

	public BigDecimal getUnitValue() {
		return unitValue;
	}

	public void setUnitValue(BigDecimal unitValue) {
		this.unitValue = unitValue;
	}

	public BigDecimal getUnitGrossValue() {
		return unitGrossValue;
	}

	public void setUnitGrossValue(BigDecimal unitGrossValue) {
		this.unitGrossValue = unitGrossValue;
	}

	public BigDecimal getTotalStValue() {
		return totalStValue;
	}

	public void setTotalStValue(BigDecimal totalStValue) {
		this.totalStValue = totalStValue;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}

	public BigDecimal getTotalGrossValue() {
		return totalGrossValue;
	}

	public void setTotalGrossValue(BigDecimal totalGrossValue) {
		this.totalGrossValue = totalGrossValue;
	}

	public BigDecimal getUnitStValueWithoutDiscount() {
		return values.get("unitStValue");
	}

	public BigDecimal getUnitValueWithoutDiscount() {
		return values.get("unitValue");
	}

	public BigDecimal getUnitGrossValueWithoutDiscount() {
		return values.get("unitGrossValue");
	}

	public BigDecimal getUnitStValueFromGBDiscount() {
		BigDecimal value = values.get("unitStValueFromGBDiscount");
		return value == null ? getUnitGrossValueWithoutDiscount() : value;
	}

	public BigDecimal getUnitValueFromGBDiscount() {
		BigDecimal value = values.get("unitValueFromGBDiscount");
		return value == null ? getUnitGrossValueWithoutDiscount() : value;
	}

	public BigDecimal getUnitGrossValueFromGBDiscount() {
		BigDecimal value = values.get("unitGrossValueFromGBDiscount");
		return value == null ? getUnitGrossValueWithoutDiscount() : value;
	}

	public BigDecimal setUnitStValueFromGBDiscount(BigDecimal value) {
		return values.put("unitStValueFromGBDiscount", value);
	}

	public BigDecimal setUnitValueFromGBDiscount(BigDecimal value) {
		return values.put("unitValueFromGBDiscount", value);
	}

	public BigDecimal setUnitGrossValueFromGBDiscount(BigDecimal value) {
		return values.put("unitGrossValueFromGBDiscount", value);
	}

	public BigDecimal getTotalValueWithoutDiscount() {
		return this.getUnitValueWithoutDiscount().multiply(new BigDecimal(this.quantity));
	}

	public BigDecimal getTotalStValueWithoutDiscount() {
		return this.getUnitStValueWithoutDiscount().multiply(new BigDecimal(this.quantity));
	}

	public BigDecimal getTotalGrossWithoutDiscount() {
		return this.getUnitGrossValueWithoutDiscount().multiply(new BigDecimal(this.quantity));
	}

	public BigDecimal getTotalGrossAfterGlobalDiscount() {
		return this.getUnitGrossValueFromGBDiscount().multiply(new BigDecimal(this.quantity));
	}
}
