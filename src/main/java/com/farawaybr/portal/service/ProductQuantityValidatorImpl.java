package com.farawaybr.portal.service;

import javax.enterprise.context.ApplicationScoped;

import com.farawaybr.portal.vo.ProductPriceData;

@ApplicationScoped
public class ProductQuantityValidatorImpl implements ProductQuantityValidator {

	@Override
	public boolean validate(ProductPriceData product) {
		int quantity = product.getQuantity();
		int multiple = product.getMultiple();
		return quantity % multiple == 0;
	}

}
