package com.farawaybr.portal.service;

import com.farawaybr.portal.vo.ProductPriceData;

public interface ProductCalculator {

	void quantity(int newQuantity, ProductPriceData product);
}
