package com.portal.client.vo.builder;

import java.math.BigDecimal;

import com.portal.client.vo.ProductDiscountData;

public class ProductDiscountDataBuilder {

	private ProductDiscountData data;

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

	public ProductDiscountDataBuilder withTotalValue(BigDecimal value) {
		data.setTotalValue(value);
		return this;
	}

	public ProductDiscountDataBuilder withTotalStValue(BigDecimal value) {
		data.setTotalStValue(value);
		return this;
	}

	public ProductDiscountDataBuilder withTotalGrossValue(BigDecimal value) {
		data.setTotalGrossValue(value);
		return this;
	}

	public ProductDiscountData build() {

		return data;
	}
}
