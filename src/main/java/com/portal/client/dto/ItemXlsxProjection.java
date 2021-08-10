package com.portal.client.dto;

public class ItemXlsxProjection {

	private String code;
	private int quantity;

	public ItemXlsxProjection(String code, int quantity) {
		super();
		this.code = code;
		this.quantity = quantity;
	}

	public String getCode() {
		return code;
	}

	public int getQuantity() {
		return quantity;
	}

	@Override
	public String toString() {
		return "ItemXlsxProjection [code=" + code + ", quantity=" + quantity + "]";
	}

}
