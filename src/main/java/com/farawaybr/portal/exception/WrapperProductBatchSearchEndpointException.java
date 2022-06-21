package com.farawaybr.portal.exception;

import com.farawaybr.portal.vo.WrapperProductBatchSearchEndpointError.ProductBatchSearchEndpointError;

public class WrapperProductBatchSearchEndpointException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -393430901125351070L;

	private ProductBatchSearchEndpointError[] products;

	private int httpStatus;

	public WrapperProductBatchSearchEndpointException(ProductBatchSearchEndpointError[] products, int status) {
		super();
		this.products = products.clone();
		this.httpStatus = status;
	}

	public ProductBatchSearchEndpointError[] getProducts() {
		return products.clone();
	}

	public int getHttpStatus() {
		return httpStatus;
	}
}
