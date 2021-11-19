package com.portal.client.vo.builder;

import java.math.BigDecimal;

import com.portal.client.vo.ProductDiscountData;

public class ProductDiscountDataBuilder {

	private ProductDiscountData data;
	private Integer quantity;

	public static ProductDiscountDataBuilder getInstance() {
		return new ProductDiscountDataBuilder();
	}

	private ProductDiscountDataBuilder() {
		data = new ProductDiscountData();
	}

	public ProductDiscountDataBuilder withDiscount(float discount) {
		data.setDiscount(discount);
		return this;
	}

	public ProductDiscountDataBuilder withGlobalDiscount(float discount) {
		data.setBudgetGlobalDiscount(discount);
		return this;
	}

	public ProductDiscountDataBuilder withUnitValue(BigDecimal value) {
		data.setUnitValue(value);
		return this;
	}

	public ProductDiscountDataBuilder withUnitStValue(BigDecimal value) {
		data.setUnitStValue(value);
		return this;
	}

	public ProductDiscountDataBuilder withUnitGrossValue(BigDecimal value) {
		data.setUnitGrossValue(value);
		return this;
	}

	public ProductDiscountDataBuilder withQuantity(int quantity) {
		this.quantity = quantity;
		return this;
	}

	public ProductDiscountData build() {
		if (quantity != null) {
			data.setTotalGrossValue(data.getUnitGrossValue().multiply(BigDecimal.valueOf(quantity)));
			data.setTotalGrossValue(data.getUnitValue().multiply(BigDecimal.valueOf(quantity)));
			data.setTotalGrossValue(data.getUnitStValue().multiply(BigDecimal.valueOf(quantity)));
		}
		return data;
	}
}
