package com.portal.client.service;

import com.portal.client.dto.ProductValue;

public interface ProductCalculator {

	void quantity(int newQuantity, ProductValue product);
}
