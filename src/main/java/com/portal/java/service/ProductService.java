package com.portal.java.service;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import com.portal.java.dto.BaseProductDTO;
import com.portal.java.dto.Product;
import com.portal.java.dto.ProductPageDTO;

public interface ProductService extends ServiceSerializable {

	Optional<ProductPageDTO> findByDescription(String descriptio, int page, int pageSize)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	Optional<Product> findByCode(String code, String customerCode, String store)
			throws SocketTimeoutException, ConnectException, TimeoutException, SocketException;

	/**
	 * Call calculateProductDiscount() to calculate new unit values and then
	 * calculateProductQuantity() to calculate new totals values;
	 * 
	 * @param product
	 */
	void calculateProduct(Product product);

	/**
	 * Calculates totals for an object that already has the quantity field
	 * updated.
	 * 
	 * @param product
	 */
	void calculateProductQuantity(Product product);

	/**
	 * Calculates unit values for an object that already has the discount field
	 * updated.
	 * 
	 * @param product
	 */
	void calculateProductDiscount(Product product);

	/**
	 * Calculates unit values based on new discount amount
	 * 
	 * @param discount
	 */
	void calculateProductDiscount(Product product,BigDecimal discount);

	void loadImage(Collection<? extends BaseProductDTO> products);

	void loadImage(Product productDTO);

}
