package com.portal.client.dto;

import java.math.BigDecimal;

public class ProductValue {

	private BigDecimal unitStValue;
	private BigDecimal unitValue;
	private BigDecimal unitGrossValue;
	private BigDecimal totalStValue;
	private BigDecimal totalValue;
	private BigDecimal totalGrossValue;
	private int quantity;
	private Integer multiple;

	public ProductValue() {
		// TODO Auto-generated constructor stub
	}

	public ProductValue(BigDecimal unitStValue, BigDecimal unitValue, BigDecimal unitGrossValue, int quantity,
			Integer multiple) {
		super();
		this.unitStValue = unitStValue;
		this.unitValue = unitValue;
		this.unitGrossValue = unitGrossValue;
		this.quantity = quantity;
		this.multiple = multiple;

		if (quantity == 1) {
			this.totalGrossValue = unitGrossValue;
			this.totalStValue = unitStValue;
			this.totalValue = unitValue;
		} else {
			BigDecimal quantityBg = new BigDecimal(quantity);
			this.totalGrossValue = unitGrossValue.multiply(quantityBg);
			this.totalStValue = unitStValue.multiply(quantityBg);
			this.totalValue = unitValue.multiply(quantityBg);
		}
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Integer getMultiple() {
		return multiple;
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

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

}
