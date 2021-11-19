package com.portal.client.service;

import com.portal.client.vo.ProductPriceData;

public interface ProductCalculator {

	void quantity(int newQuantity, ProductPriceData product);
}
