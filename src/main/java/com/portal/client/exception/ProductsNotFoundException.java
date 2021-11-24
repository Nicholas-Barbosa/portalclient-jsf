package com.portal.client.exception;

import com.portal.client.vo.WrapperProduct404Error.Product404Error;

public class ProductsNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -393430901125351070L;

	private Product404Error[] errors;

	public ProductsNotFoundException(Product404Error[] errors) {
		super();
		this.errors = errors.clone();
	}

	public Product404Error[] getErrors() {
		return errors.clone();
	}
}
