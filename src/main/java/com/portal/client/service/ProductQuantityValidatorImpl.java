package com.portal.client.service;

import javax.enterprise.context.ApplicationScoped;

import com.portal.client.vo.ProductPriceData;

@ApplicationScoped
public class ProductQuantityValidatorImpl implements ProductQuantityValidator {

	@Override
	public boolean validate(ProductPriceData product) {
		int quantity = product.getQuantity();
		int multiple = product.getMultiple();
		return quantity % multiple == 0;
	}

}
