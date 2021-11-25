package com.portal.client.exception;

import com.portal.client.vo.WrapperProduct404Error.Product404Error;

public class ProductsNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -393430901125351070L;

	private Product404Error[] products;

	public ProductsNotFoundException(Product404Error[] products) {
		super();
		this.products = products.clone();
	}

	public Product404Error[] getProducts() {
		return products.clone();
	}
}
