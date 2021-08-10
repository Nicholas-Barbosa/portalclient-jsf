package com.portal.client.dto;

public class ItemXlsxProjection {

	private String code;
	private int quantity;

	public ItemXlsxProjection() {
		// TODO Auto-generated constructor stub
	}

	public ItemXlsxProjection(String code, int quantity) {
		super();
		this.code = code;
		this.quantity = quantity;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "ItemXlsxProjection [code=" + code + ", quantity=" + quantity + "]";
	}

}
