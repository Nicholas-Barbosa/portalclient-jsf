package com.farawaybr.portal.dto;

public class ProductImportXlsxCustomizedRow {

	private int row, quantity;
	private String product;

	public ProductImportXlsxCustomizedRow(int row, int quantity, String product) {
		super();
		this.row = row;
		this.quantity = quantity;
		this.product = product;
	}

	public int getRow() {
		return row;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getProduct() {
		return product;
	}

}
