package com.portal.client.dto;

import java.math.BigDecimal;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class ItemBudgetProjection {

	private BigDecimal grossValue;
	private float lineDiscount;
	private final String commercialCode;
	private final String productCode;
	private final BigDecimal unitValue;
	private int quantity;
	private BigDecimal total;
	private BigDecimal stValue;

	@JsonbCreator
	public static ItemBudgetProjection ofJsonb(@JsonbProperty("unit_gross_value") BigDecimal grossValue,
			@JsonbProperty("line_discount") float lineDiscount, @JsonbProperty("commercial_code") String commercialCode,
			@JsonbProperty("product_code") String productCode, @JsonbProperty("unit_price") BigDecimal unitValue,
			@JsonbProperty("quantity") int quantity, @JsonbProperty("total_price") BigDecimal total,
			@JsonbProperty("st_value") BigDecimal stValue) {
		return new ItemBudgetProjection(grossValue, lineDiscount, commercialCode, productCode, unitValue, quantity,
				total, stValue);
	}

	public ItemBudgetProjection(BigDecimal grossValue, float lineDiscount, String commercialCode, String productCode,
			BigDecimal unitValue, int quantity, BigDecimal total, BigDecimal stValue) {
		super();
		this.grossValue = grossValue;
		this.lineDiscount = lineDiscount;
		this.commercialCode = commercialCode;
		this.productCode = productCode;
		this.unitValue = unitValue;
		this.quantity = quantity;
		this.total = total;
		this.stValue = stValue;
	}

	public BigDecimal getGrossValue() {
		return grossValue;
	}

	public float getLineDiscount() {
		return lineDiscount;
	}

	public String getCommercialCode() {
		return commercialCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public BigDecimal getUnitValue() {
		return unitValue;
	}

	public int getQuantity() {
		return quantity;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public BigDecimal getStValue() {
		return stValue;
	}

}
