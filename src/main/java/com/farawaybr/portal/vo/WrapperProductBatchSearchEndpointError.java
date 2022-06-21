package com.farawaybr.portal.vo;

import java.io.Serializable;
import java.util.Arrays;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class WrapperProductBatchSearchEndpointError implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8361543285619261063L;
	private ProductBatchSearchEndpointError[] errors;

	@JsonbProperty("error")
	public void setErrors(ProductBatchSearchEndpointError[] errors) {
		this.errors = errors == null ? errors : errors.clone();
	}

	public ProductBatchSearchEndpointError[] getErrors() {
		return errors;
	}

	
	@Override
	public String toString() {
		return "WrapperProduct404Error [errors=" + Arrays.toString(errors) + "]";
	}


	public static class ProductBatchSearchEndpointError implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -1775166016929585854L;
		private String cause, productIdentity;

		public ProductBatchSearchEndpointError() {
			// TODO Auto-generated constructor stub
		}

		@JsonbCreator
		public ProductBatchSearchEndpointError(@JsonbProperty("cause") String cause, @JsonbProperty("itendity") String productIdentity) {
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