package com.portal.client.exception;

import com.portal.client.vo.WrapperItem404Error.Item404Error;

public class ItemsNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -393430901125351070L;

	private Item404Error[] errors;

	public ItemsNotFoundException(Item404Error[] errors) {
		super();
		this.errors = errors.clone();
	}

	public Item404Error[] getErrors() {
		return errors.clone();
	}
}
