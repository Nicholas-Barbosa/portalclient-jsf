package com.portal.client.service;

import java.math.BigDecimal;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.portal.client.dto.ProductValue;
import com.portal.client.util.MathUtils;

@ApplicationScoped
public class ProductCalculatorImpl implements ProductCalculator {

	private ProductQuantityValidator quantityValidator;

	@Inject
	public ProductCalculatorImpl(ProductQuantityValidator quantityValidator) {
		super();
		this.quantityValidator = quantityValidator;
	}

	@Override
	public void quantity(int newQuantity, ProductValue value) {
		if (quantityValidator.validate(value)) {
			BigDecimal unitValue = value.getUnitValue();
			BigDecimal unitStValue = value.getUnitStValue();
			BigDecimal unitGrossValue = value.getUnitGrossValue();

			value.setTotalValue(MathUtils.calculateTotalValueOverQuantity(newQuantity, unitValue));
			value.setTotalStValue(MathUtils.calculateTotalValueOverQuantity(newQuantity, unitStValue));
			value.setTotalGrossValue(MathUtils.calculateTotalValueOverQuantity(newQuantity, unitGrossValue));
			value.setQuantity(newQuantity);
		}
	}

}
