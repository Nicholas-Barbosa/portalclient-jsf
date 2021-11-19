package com.portal.client.vo.builder;

import java.math.BigDecimal;

import com.portal.client.vo.ProductDiscountData;
import com.portal.client.vo.ProductPriceData;

public class ProductPriceBuilder {

	private ProductPriceData instance;

	public static ProductPriceBuilder getInstance() {
		return new ProductPriceBuilder();
	}

	private ProductPriceBuilder() {
		this.instance = new ProductPriceData();
	}

	public ProductPriceBuilder withUnitStValue(BigDecimal value) {
		instance.setUnitStValue(value);
		return this;
	}

	public ProductPriceBuilder withUnitValue(BigDecimal value) {
		instance.setUnitValue(value);
		return this;
	}

	public ProductPriceBuilder withUnitGrossValue(BigDecimal value) {
		instance.setUnitGrossValue(value);
		return this;
	}

	public ProductPriceBuilder withTotalStValue(BigDecimal value) {
		instance.setTotalStValue(value);
		return this;
	}

	public ProductPriceBuilder withTotalValue(BigDecimal value) {
		instance.setTotalValue(value);
		return this;
	}

	public ProductPriceBuilder withTotalGrossValue(BigDecimal value) {
		instance.setTotalGrossValue(value);
		return this;
	}

	public ProductPriceBuilder withQuantity(int value) {
		instance.setQuantity(value);
		return this;
	}

	public ProductPriceBuilder withMultiple(int value) {
		instance.setMultiple(value);
		return this;
	}

	public ProductPriceBuilder withDiscountData(ProductDiscountData value) {
		instance.setDiscountData(value);
		return this;
	}

	public ProductPriceData build() {
		return instance;
	}
}
