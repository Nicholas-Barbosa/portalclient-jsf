package com.portal.client.vo;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class WrapperItemError {

	private ItemError[] errors;

	@JsonbProperty("error")
	public void setErrors(ItemError[] errors) {
		this.errors = errors.clone();
	}

	public ItemError[] getErrors() {
		return errors;
	}

	public static class ItemError {
		private String cause, itemIdentity;

		@JsonbCreator
		public ItemError(@JsonbProperty("cause") String cause, @JsonbProperty("itendity") String itemIdentity) {
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