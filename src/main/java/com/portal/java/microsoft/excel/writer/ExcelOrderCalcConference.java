package com.portal.java.microsoft.excel.writer;

import java.math.BigDecimal;

public class ExcelOrderCalcConference {

	private String productCode;
	private String productLine;
	private int productQuantity;
	private BigDecimal unitValueWithoutDiscount;
	private BigDecimal totalValueWithoutDiscount;
	private BigDecimal totalStValueWithoutDiscount;
	private BigDecimal grossValueWithouDiscount;
	private float globalDiscount;
	private BigDecimal globalDiscountValue;
	private float lineDiscount;
	private BigDecimal lineDiscountValue;
	private BigDecimal totalValueWithDiscount;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductLine() {
		return productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public BigDecimal getUnitValueWithoutDiscount() {
		return unitValueWithoutDiscount;
	}

	public void setUnitValueWithoutDiscount(BigDecimal unitValueWithoutDiscount) {
		this.unitValueWithoutDiscount = unitValueWithoutDiscount;
	}

	public BigDecimal getTotalValueWithoutDiscount() {
		return totalValueWithoutDiscount;
	}

	public void setTotalValueWithoutDiscount(BigDecimal totalValueWithoutDiscount) {
		this.totalValueWithoutDiscount = totalValueWithoutDiscount;
	}

	public BigDecimal getTotalStValueWithoutDiscount() {
		return totalStValueWithoutDiscount;
	}

	public void setTotalStValueWithoutDiscount(BigDecimal totalStValueWithoutDiscount) {
		this.totalStValueWithoutDiscount = totalStValueWithoutDiscount;
	}

	public BigDecimal getGrossValueWithouDiscount() {
		return grossValueWithouDiscount;
	}

	public void setGrossValueWithouDiscount(BigDecimal grossValueWithouDiscount) {
		this.grossValueWithouDiscount = grossValueWithouDiscount;
	}

	public float getGlobalDiscount() {
		return globalDiscount;
	}

	public void setGlobalDiscount(float globalDiscount) {
		this.globalDiscount = globalDiscount;
	}

	public BigDecimal getGlobalDiscountValue() {
		return globalDiscountValue;
	}

	public void setGlobalDiscountValue(BigDecimal globalDiscountValue) {
		this.globalDiscountValue = globalDiscountValue;
	}

	public float getLineDiscount() {
		return lineDiscount;
	}

	public void setLineDiscount(float lineDiscount) {
		this.lineDiscount = lineDiscount;
	}

	public BigDecimal getLineDiscountValue() {
		return lineDiscountValue;
	}

	public void setLineDiscountValue(BigDecimal lineDiscountValue) {
		this.lineDiscountValue = lineDiscountValue;
	}

	public BigDecimal getTotalValueWithDiscount() {
		return totalValueWithDiscount;
	}

	public void setTotalValueWithDiscount(BigDecimal totalValueWithDiscount) {
		this.totalValueWithDiscount = totalValueWithDiscount;
	}

}
