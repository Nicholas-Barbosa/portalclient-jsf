package com.farawaybr.portal.vo;

import java.io.Serializable;
import java.util.Arrays;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class WrapperProduct404Error implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8361543285619261063L;
	private Product404Error[] errors;

	@JsonbProperty("error")
	public void setErrors(Product404Error[] errors) {
		this.errors = errors == null ? errors : errors.clone();
	}

	public Product404Error[] getErrors() {
		return errors;
	}

	
	@Override
	public String toString() {
		return "WrapperProduct404Error [errors=" + Arrays.toString(errors) + "]";
	}


	public static class Product404Error implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -1775166016929585854L;
		private String cause, productIdentity;

		public Product404Error() {
			// TODO Auto-generated constructor stub
		}

		@JsonbCreator
		public Product404Error(@JsonbProperty("cause") String cause, @JsonbProperty("itendity") String productIdentity) {
			super();
			this.cause = cause;
			this.productIdentity = productIdentity;
		}

		public String getCause() {
			return cause;
		}

		public String getProductIdentity() {
			return productIdentity;
		}

		@Override
		public String toString() {
			return "WrapperProduct404Error [cause=" + cause + ", itemIdentity=" + productIdentity + "]";
		}

	}

}