package com.portal.client.vo;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class WrapperItem404Error {

	private Item404Error[] errors;

	@JsonbProperty("error")
	public void setErrors(Item404Error[] errors) {
		this.errors = errors.clone();
	}

	public Item404Error[] getErrors() {
		return errors;
	}

	public static class Item404Error {
		private String cause, itemIdentity;

		public Item404Error() {
			// TODO Auto-generated constructor stub
		}

		@JsonbCreator
		public Item404Error(@JsonbProperty("cause") String cause, @JsonbProperty("itendity") String itemIdentity) {
			super();
			this.cause = cause;
			this.itemIdentity = itemIdentity;
		}

		public String getCause() {
			return cause;
		}

		public String getItemIdentity() {
			return itemIdentity;
		}

		@Override
		public String toString() {
			return "ItemError [cause=" + cause + ", itemIdentity=" + itemIdentity + "]";
		}

	}

}