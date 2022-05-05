package com.farawaybr.portal.vo;

import java.math.BigDecimal;

public class BaseProductPriceState {
	
	
	protected BigDecimal unitStValue;
	protected BigDecimal unitValue;
	protected BigDecimal unitGrossValue;
	protected BigDecimal totalStValue;
	protected BigDecimal totalValue;
	protected BigDecimal totalGrossValue;
	
	public BaseProductPriceState() {
		// TODO Auto-generated constructor stub
	}

	public BaseProductPriceState(BigDecimal unitStValue, BigDecimal unitValue, BigDecimal unitGrossValue,
			BigDecimal totalStValue, BigDecimal totalValue, BigDecimal totalGrossValue) {
		super();
		this.unitStValue = unitStValue;
		this.unitValue = unitValue;
		this.unitGrossValue = unitGrossValue;
		this.totalStValue = totalStValue;
		this.totalValue = totalValue;
		this.totalGrossValue = totalGrossValue;
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
	
	
	
}
