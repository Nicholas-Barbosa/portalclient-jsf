package com.portal.java.dto;

import java.math.BigDecimal;

public class ProductPrice {

	private final BigDecimal unitStValue;
	private final BigDecimal unitValue;
	private final BigDecimal unitGrossValue;

	public ProductPrice(BigDecimal unitStValue, BigDecimal unitValue, BigDecimal unitGrossValue) {
		super();
		this.unitStValue = unitStValue;
		this.unitValue = unitValue;
		this.unitGrossValue = unitGrossValue;
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

}
