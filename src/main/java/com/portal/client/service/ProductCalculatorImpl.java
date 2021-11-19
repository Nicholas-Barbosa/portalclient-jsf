package com.portal.client.service;

import java.math.BigDecimal;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.vo.ProductPriceData;

@ApplicationScoped
public class ProductCalculatorImpl implements ProductCalculator {

	private ProductQuantityValidator quantityValidator;

	@Inject
	public ProductCalculatorImpl(ProductQuantityValidator quantityValidator) {
		super();
		this.quantityValidator = quantityValidator;
	}

	@Override
	public void quantity(int newQuantity, ProductPriceData value) {
		if (quantityValidator.validate(value)) {
			BigDecimal unitValue = value.getUnitValue();
			BigDecimal unitStValue = value.getUnitStValue();
			BigDecimal unitGrossValue = value.getUnitGrossValue();
			BigDecimal quantity = BigDecimal.valueOf(newQuantity);
			value.setTotalValue(unitValue.multiply(quantity));
			value.setTotalStValue(unitStValue.multiply(quantity));
			value.setTotalGrossValue(unitGrossValue.multiply(quantity));
			value.setQuantity(newQuantity);
		}
	}

}
